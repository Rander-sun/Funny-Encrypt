package sample;
import java.util.Scanner;

public class TestMain {
    public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    App app = new App();
    App.encryptVideo(in);
    App.decryptVideo(in);
    return;
    }
}
