use std::env::args;

mod knn_model {
    use core::fmt;
    use std::fs;

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

    pub struct Model {
        model_name: String,
        k_value: i32,
        training_data: Vec<Instance>,
        testing_data: Vec<Instance>,
        feature_bounds: Vec<(f64, f64)>,
    }

    impl Model {
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

        fn find_bounds(instances: &Vec<Instance>) -> Vec<(f64, f64)> {
            vec![(0.0, 0.0)]
        }

        fn find_distance(&self, a: &Instance, b: &Instance) -> f64 {
            a.input_features
                .iter()
                .zip(&b.input_features)
                .zip(&self.feature_bounds)
                .map(|f| -> f64 { ((f.0 .0 + f.0 .1).powi(2)) / (f.1 .1 - f.1 .0) })
                .sum::<f64>()
                .sqrt()
        }

        pub fn train(model_name: &str, training: &str, testing: &str, k_value: i32) -> Model {
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
                training_data,
                testing_data,
                feature_bounds,
            }
        }

        pub fn test(&self) {
            //

            println!("{}", &self);
        }
    }

    impl fmt::Display for Model {
        fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
            let mut training = String::new();
            let mut testing = String::new();

            for instance in &self.training_data {
                training.push_str(&format!("\n{}", &instance));
            }
            for instance in &self.testing_data {
                testing.push_str(&format!("\n{}", &instance));
            }

            write!(
                f,
                "Model \"{}\" at k={}\nTraining Instances:\n{}\n\nTesting Instances:\n{}",
                self.model_name,
                self.k_value,
                &training[1..],
                &testing[1..]
            )
        }
    }
}

fn main() {
    let args: Vec<_> = args().collect();
    if args.len() < 3 {
        panic!("Must include training and testing data.")
    };
    let model = knn_model::Model::train("Wine Classification", &args[1], &args[2], 3);

    model.test();
}
