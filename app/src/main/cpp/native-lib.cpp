#include <jni.h>
#include <string>
#include <fstream>
#include <sstream>

std::string stdStringFromJString(JNIEnv *env, jstring inputJString){
    std::string retValueStr{};

    jboolean isCopy;
    const char *convertedValue = (env)->GetStringUTFChars(inputJString, &isCopy);
    if(convertedValue)
    {
        retValueStr = std::string(convertedValue);
        env->ReleaseStringUTFChars(inputJString, convertedValue);
    }

    return retValueStr;
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_pkgsinfo_MainActivity_getFileTextBuffer(
        JNIEnv* env,
        jobject /* this */, jstring fileNameJ) {


    std::string ret_val_str;

    std::string fileName = stdStringFromJString(env, fileNameJ);
    std::ifstream file;
    file.open(fileName);
    if(file.is_open())
    {
        std::ostringstream ss;
        ss << file.rdbuf();
        ret_val_str = ss.str();
    }
    else
        ret_val_str = "Cannot open file " + fileName;

    return env->NewStringUTF(ret_val_str.c_str());
}
