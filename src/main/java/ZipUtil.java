import net.lingala.zip4j.ZipFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
    public static void unzip(String sourceFile, String targetPath) throws IOException {
        new ZipFile(Paths.get(sourceFile).toFile()).extractAll(Paths.get(targetPath).toString());
    }

    // Deletes source folder once zip is complete
    public static void zipDirectory(String zipFile, String folderToZip) throws IOException {
        byte[] buffer = new byte[1024];
        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream zos = new ZipOutputStream(fos);
        File dir = new File(folderToZip);
        File[] files = dir.listFiles();

        for (File file : files) {
            FileInputStream fis = new FileInputStream(file);

            //write a new ZIP entry
            zos.putNextEntry(new ZipEntry(file.getName()));

            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }
            zos.closeEntry();
            fis.close();
            file.delete();
        }
        //close the ZipOutputStream
        zos.close();
        dir.delete();
    }


    public static void main(String[] args) {
        String ZIP_PATH = "src/main/plzs/";

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
