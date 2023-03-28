use std::env::args;

#[allow(dead_code)]
mod knn_model {
    use std::fs;

    #[derive(Default)]
    pub struct Instance {
        input_features: Vec<f64>,
        class: u32,
    }

    pub struct Model {
        model_name: String,
        k_value: i32,
        training_data: Vec<Instance>,
        testing_data: Vec<Instance>,
    }

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

    impl Model {
        pub fn new(model_name: &str, training: &str, testing: &str, k_value: i32) -> Model {
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
            let training_data = retreive_instances(&training_input[1..]);
            let testing_data = retreive_instances(&testing_input[1..]);

            Model {
                model_name: String::from(model_name),
                k_value,
                training_data,
                testing_data,
            }
        }

        pub fn test(&self) {
            self.print_model();
        }

        fn print_model(&self) {
            println!("{}", self.model_name);
            println!("Training Data:");
            for instance in &self.training_data {
                for feature in &instance.input_features {
                    print!("{} ", feature);
                }
                println!("with a class of {}", instance.class);
            }
            println!("Testing Data:");
            for instance in &self.testing_data {
                for feature in &instance.input_features {
                    print!("{} ", feature);
                }
                println!("with a class of {}", instance.class);
            }
        }
    }
}

fn main() {
    let args: Vec<_> = args().collect();
    if args.len() < 3 {
        panic!("Must include training and testing data.")
    };
    let model = knn_model::Model::new("Wine Classification Model", &args[1], &args[2], 3);

    model.test();
}
