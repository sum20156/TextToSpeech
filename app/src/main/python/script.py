from gtts import gTTS
import os.path

def main(path,name_of_file,app_text):
    language = 'en' #hindi
    speech = gTTS(text = app_text, lang = language, slow = False)
    completeName = os.path.join(path, name_of_file)
    speech.save(completeName)
    return True

