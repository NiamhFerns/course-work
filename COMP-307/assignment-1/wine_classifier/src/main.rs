use std::env::args;

mod knn_model {
    use core::fmt;
    use std::fs;

    // Input features and class for a nominal instance in a knn classifier model.
    #[derive(Default)]
    struct Instance {
        input_features: Vec<f64>,
        class: u32,
    }

    impl fmt::Display for Instance {
        fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
            let mut s = String::new();

            for feature in &self.input_features {
                s.push_str(&format!("{}{}", ", ", &feature.to_string()));
            }

            write!(f, "Class: \"{}\" Input Features: {}", &self.class, &s[2..])
        }
    }

    // Relevant data for a kNN model as well as it's testing data.
    pub struct Model {
        model_name: String,
        k_value: usize,
        class_count: u32,
        training_data: Vec<Instance>,
        testing_data: Vec<Instance>,
        feature_bounds: Vec<(f64, f64)>,
    }

    impl Model {
        // Read in all the instances for a data set or partition and return as a vector of
        // instances.
        fn retreive_instances(instances: &[&str]) -> Vec<Instance> {
            instances
                .iter()
                .map(|&f| {
                    let input_features: Vec<_> = f.split(" ").collect();
                    Instance {
                        input_features: input_features[..13]
                            .iter()
                            .map(|&f| {
                                f.trim()
                                    .parse::<f64>()
                                    .expect("Failed to parse in an input feature of an instance.")
                            })
                            .collect(),
                        class: input_features
                            .last()
                            .expect("Zero input features supplied for an instance.")
                            .parse::<u32>()
                            .expect("Unable to parse in the class of an instance."),
                    }
                })
                .collect()
        }

        // Given a list of training instances, return a vector of (max, min) tuples containing the
        // bounds for each feature.
        fn find_bounds(instances: &Vec<Instance>) -> Vec<(f64, f64)> {
            let mut bounds = vec![(0.0, f64::INFINITY); 13];
            for instance in instances {
                bounds = bounds
                    .iter()
                    .zip(&instance.input_features)
                    .map(|(bound, f)| -> (f64, f64) {
                        (f64::max(*f, bound.0), f64::min(*f, bound.1))
                    })
                    .collect::<Vec<(f64, f64)>>()
            }
            bounds
        }

        // Given instances a and b, return the geometrical distance normalised by the training data.
        fn find_distance(&self, a: &Instance, b: &Instance) -> f64 {
            a.input_features
                .iter()
                .zip(&b.input_features)
                .zip(&self.feature_bounds)
                .map(|((a, b), (max, min))| -> f64 { ((a + b).powi(2)) / (max - min) })
                .sum::<f64>()
                .sqrt()
        }

        // Trains a kNN model by retreiving all the instances needed for the training data as well
        // as any testing instances needed.
        pub fn train(
            model_name: &str,
            training: &str,
            testing: &str,
            k_value: usize,
            class_count: u32,
        ) -> Model {
            /*
             * Input features read in in order of the Instance struct's members.
             * 14th value is the class of that instance.
             */
            let training_input =
                fs::read_to_string(training).expect("Failed to read in file for training values.");
            let testing_input =
                fs::read_to_string(testing).expect("Failed to read in file for training values.");

            let training_input: Vec<_> = training_input.trim_end().split("\n").collect();
            let testing_input: Vec<_> = testing_input.trim_end().split("\n").collect();
            let training_data = Model::retreive_instances(&training_input[1..]);

            let feature_bounds = Model::find_bounds(&training_data);

            let testing_data = Model::retreive_instances(&testing_input[1..]);

            Model {
                model_name: String::from(model_name),
                k_value,
                class_count,
                training_data,
                testing_data,
                feature_bounds,
            }
        }

        fn classify_instance(&self, instance: &Instance) -> u32 {
            // Iterate over training distances, find the distance from instance to each of them.
            let mut distances = self
                .training_data
                .iter()
                .map(|target| -> (f64, &Instance) {
                    (self.find_distance(instance, target), target)
                })
                .collect::<Vec<(f64, &Instance)>>();

            distances.sort_by(|a, b| a.0.partial_cmp(&b.0).unwrap());

            // Retreive a vector of the counts for each class up to the class count.
            let mut counts: Vec<(u32, usize)> = Vec::new();
            for i in 1..self.class_count + 1 {
                counts.push((
                    i,
                    distances[..self.k_value]
                        .iter()
                        .filter(|(_, instance)| instance.class == i)
                        .count(),
                ));
            }

            // Sort it and push this instance plus alongside the class with the highest count
            // (the end of the counts vector).
            counts.sort_by(|a, b| a.1.partial_cmp(&b.1).unwrap());
            counts
                .last()
                .expect("Tried to push a prediction without any valid classes.")
                .0
        }

        // Test the accuracy of our model at k.
        pub fn test(&self) {
            let mut predictions: Vec<(u32, &Instance)> = Vec::new();

            // Iterate over each testing instance, find the distance to all elements and then take
            // the lowest k of them.
            for instance in &self.testing_data {
                // let mut distances = self
                //     .training_data
                //     .iter()
                //     .map(|target| -> (f64, &Instance) {
                //         (self.find_distance(instance, target), target)
                //     })
                //     .collect::<Vec<(f64, &Instance)>>();
                //
                // distances.sort_by(|a, b| a.0.partial_cmp(&b.0).unwrap());
                //
                // // Retreive a vector of the counts for each class up to the class count.
                // let mut counts: Vec<(u32, usize)> = Vec::new();
                // for i in 1..self.class_count + 1 {
                //     counts.push((
                //         i,
                //         *&distances[..self.k_value]
                //             .iter()
                //             .filter(|(_, instance)| instance.class == i)
                //             .count(),
                //     ))
                // }
                //
                // // Sort it and push this instance plus alongside the class with the highest count
                // // (the end of the counts vector).
                // counts.sort_by(|a, b| a.1.partial_cmp(&b.1).unwrap());
                predictions.push((
                    self.classify_instance(instance),
                    // counts
                    //     .last()
                    //     .expect("Tried to push a prediction without any valid classes.")
                    //     .0,
                    instance,
                ));
            }

            println!("{}", &self);
        }
    }

    impl fmt::Display for Model {
        fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
            let mut training = String::new();
            let mut testing = String::new();
            let mut bounds = String::new();

            for instance in &self.training_data {
                training.push_str(&format!("\n{}", &instance));
            }
            for instance in &self.testing_data {
                testing.push_str(&format!("\n{}", &instance));
            }
            for (max, min) in &self.feature_bounds {
                bounds.push_str(&format!("\n(Max: {}, Min: {})", max, min));
            }

            write!(
                f,
                "Model \"{}\" at k={}\nTraining Instances:\n{}\n\nTesting Instances:\n{}\n\nBounds:{}",
                self.model_name,
                self.k_value,
                &training[1..],
                &testing[1..],
                &bounds[1..]
            )
        }
    }
}

fn main() {
    let args: Vec<_> = args().collect();
    if args.len() < 3 {
        panic!("Must include training and testing data.")
    };
    let model = knn_model::Model::train("Wine Classification", &args[1], &args[2], 3, 3);

    model.test();
}