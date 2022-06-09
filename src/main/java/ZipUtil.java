import net.lingala.zip4j.ZipFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
    private static final String ZIP_PATH = "src/main/plzs/";

    public static void unzip(String sourceFile, String targetPath) throws IOException {
        Path source = Paths.get(sourceFile);
        Path target = Paths.get(targetPath);

        new ZipFile(source.toFile()).extractAll(target.toString());
    }

    public static void zipDirectory(String zipFile, String saveLocation) throws IOException {
        byte[] buffer = new byte[1024];
        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream zos = new ZipOutputStream(fos);
        File dir = new File(saveLocation);
        File[] files = dir.listFiles();

        for (int i = 0; i < files.length; i++) {
            FileInputStream fis = new FileInputStream(files[i]);

            //write a new ZIP entry
            zos.putNextEntry(new ZipEntry(files[i].getName()));

            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write( buffer, 0, length );
            }

            zos.closeEntry();
            fis.close();
        }
        //close the ZipOutputStream
        zos.close();
    }


    public static void main(String[] args) {
        // Unzip PLZ test
        try {
            unzip(ZIP_PATH + "0260238A.plz", ZIP_PATH + "0260238A/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Zip folder to PLZ
        try {
            zipDirectory(ZIP_PATH + "updated/0260238A-editted.plz", ZIP_PATH + "0260238A/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
