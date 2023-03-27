use std::{env::args, fs};

#[allow(dead_code)]
mod knn_model {
    use std::fs;

    #[derive(Default)]
    pub struct Instance {
        alcohol: i32,
        malic_acid: i32,
        ash: i32,
        alcalinity_of_ash: i32,
        magnesium: i32,
        total_phenols: i32,
        flavanoids: i32,
        nonflavanoid_phenols: i32,
        proantheocyanins: i32,
        colour_intensity: i32,
        hue: i32,
        od_value: i32,
        proline: i32,
    }

    pub struct Model {
        k_value: i32,
        training_data: Vec<Instance>,
        testing_data: Vec<Instance>,
    }

    impl Model {
        pub fn train(training: &str, testing: &str, k_value: i32) -> Model {
            let training_data =
                fs::read_to_string(training).expect("Failed to read in file for training values.");
            let testing_data =
                fs::read_to_string(testing).expect("Failed to read in file for training values.");

            let training_data: Vec<_> = training_data.split("\n").collect();
            let testing_data: Vec<_> = testing_data.split("\n").collect();

            Model {
                k_value,
                training_data: vec![Instance {
                    ..Default::default()
                }],
                testing_data: vec![Instance {
                    ..Default::default()
                }],
            }
        }

        pub fn test(&self) {}
    }
}

fn main() {
    let args: Vec<_> = args().collect();
    let model = knn_model::Model::train(&args[1], &args[2], 3);

    model.test();
}
