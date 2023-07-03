#include "get_filenames.h"
#include <filesystem>

#include <android/log.h>
#define TAG "PN_Logs"

const std::vector<std::string> get_files(const std::string &current_path_str){

    std::vector<std::string> paths{};

    __android_log_print(ANDROID_LOG_VERBOSE, TAG, "%s\n", ("Current directory is: " +
        std::filesystem::current_path().string()).c_str());
    __android_log_print(ANDROID_LOG_VERBOSE, TAG, "%s\n", "..preparing to iterate..");

    const std::filesystem::path path{current_path_str};
    if(std::filesystem::exists(path) && std::filesystem::is_directory(path)) {
        __android_log_print(ANDROID_LOG_VERBOSE, TAG, "%s\n",
                            ("..OK, " + path.string() + " exists..").c_str());
        for (auto const &dir_entry: std::filesystem::directory_iterator{path}) {
            __android_log_print(ANDROID_LOG_VERBOSE, TAG, "%s\n", dir_entry.path().c_str());
            paths.push_back(dir_entry.path());
        }
    }
    else
        __android_log_print(ANDROID_LOG_VERBOSE, TAG, "%s\n",
                            (path.string() + " is not valid").c_str());
    /*
    for(const auto &file :
        // std::filesystem::directory_iterator(current_path)){
        std::filesystem::directory_iterator(temp_path)){

        __android_log_print(ANDROID_LOG_VERBOSE, TAG, "%s\n", file.path().c_str());

        //__android_log_print(ANDROID_LOG_VERBOSE, TAG, "%s", file.path().c_str());
		if(std::filesystem::is_directory(file)){
            __android_log_print(ANDROID_LOG_VERBOSE, TAG, "%s\n", "is directory");
			get_files(file.path().string());
            //paths.push_back(file.path().generic_string()); // add dir to vector
		}
		else{
            __android_log_print(ANDROID_LOG_VERBOSE, TAG, "%s\n", file.path().c_str());
            paths.push_back(file.path().filename().string());
        }
    }
     */

    __android_log_print(ANDROID_LOG_VERBOSE, TAG, "%s\n", "return..");
    return paths;
}
