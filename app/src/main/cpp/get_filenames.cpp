#include "get_filenames.h"
#include <filesystem>

void get_files(std::vector<std::string> &paths,
		const std::string &current_path){

    for(const auto &file :
         std::filesystem::directory_iterator(current_path)){
		if(std::filesystem::is_directory(file)){
			get_files(paths, file.path().string());
            //paths.push_back(file.path().generic_string()); // add dir to vector
		}
		else{
            paths.push_back(file.path().string());
        }
    }
}
