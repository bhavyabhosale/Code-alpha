import com.google.cloud.texttospeech.v1.*;
import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class SpeechSystem {

    private final TextToSpeechClient textToSpeechClient;
    private final SpeechClient speechClient;

    public SpeechSystem() throws IOException {
        // Initialize TextToSpeechClient and SpeechClient
        textToSpeechClient = TextToSpeechClient.create();
        speechClient = SpeechClient.create();
    }

    public ByteString textToSpeech(String text) {
        // Text-to-Speech
        SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();
        VoiceSelectionParams voice =
                VoiceSelectionParams.newBuilder().setLanguageCode("en-US").setName("en-US-Wavenet-D").build();
        AudioConfig audioConfig = AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.LINEAR16).build();
        SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);
        return response.getAudioContent();
    }

    public String speechToText(ByteString audioBytes) {
        // Speech-to-Text
        RecognitionConfig config =
                RecognitionConfig.newBuilder().setEncoding(RecognitionConfig.AudioEncoding.LINEAR16).build();
        RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(audioBytes).build();
        RecognizeResponse response = speechClient.recognize(config, audio);
        List<SpeechRecognitionResult> results = response.getResultsList();
        StringBuilder transcript = new StringBuilder();
        for (SpeechRecognitionResult result : results) {
            transcript.append(result.getAlternatives(0).getTranscript());
        }
        return transcript.toString();
    }

    public static void main(String[] args) throws IOException {
        SpeechSystem speechSystem = new SpeechSystem();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter text: ");
        String userInput = scanner.nextLine();

        ByteString audioBytes = speechSystem.textToSpeech(userInput);
        String transcript = speechSystem.speechToText(audioBytes);

        System.out.println("Original Text: " + userInput);
        System.out.println("Transcript: " + transcript);

        // Close the scanner and clients
        scanner.close();
        speechSystem.textToSpeechClient.close();
        speechSystem.speechClient.close();
    }
}
