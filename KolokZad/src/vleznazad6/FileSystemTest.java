package vleznazad6;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class FileNameExistsException extends Exception{
    public FileNameExistsException(String message) {
        super(message);
    }
}

interface IFile{
    String getFileName();
    long getFileSize();

    String getFileInfo(String tabs);

    void sortBySize();
    long findLargestFile();

}

class File implements IFile {
    String fileName;

    long fileSize;
    public File(String fileName, long fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public long getFileSize() {
        return fileSize;
    }


    @Override
    public String getFileInfo(String tabs) {
        return tabs + String.format("File name: %10s File size: %10d\n",fileName,fileSize);
    }

    @Override
    public void sortBySize() {
    }

    @Override
    public long findLargestFile() {
        return 0;
    }

    @Override
    public String toString() {
        return "File{}";
    }
}

class Folder implements IFile{

    String folderName;
    long folderSize;
    List<IFile> files;

    public Folder(String line) {
        this.folderName = line;
        files = new ArrayList<>();
    }



    @Override
    public String getFileName() {
        return folderName;
    }

    @Override
    public long getFileSize() {
        return files.stream()
                .mapToLong(IFile -> IFile.getFileSize())
                .sum();
    }

    @Override
    public String getFileInfo(String tabs) {
        String result =tabs + String.format("Folder name: %10s Folder size: %10d\n",folderName,getFileSize());
        for(IFile file : files) {
            result +=  file.getFileInfo(tabs + "\t");
        }
        return result;
    }

    @Override
    public void sortBySize() {
        Comparator<IFile> comparator = Comparator.comparing(IFile::getFileSize);
        files.sort(comparator);
        files.forEach(IFile::sortBySize);
    }

    @Override
    public long findLargestFile() {
        List<Long> sizes = new ArrayList<>();
        sizes.add(files.stream().filter(file -> file.getClass().equals(File.class))
                .mapToLong(IFile::getFileSize)
                .max().orElse(0));
        for (IFile file: files){
            sizes.add(file.findLargestFile());
        }
        return sizes.stream().mapToLong(Long::longValue).max().orElse(0);
    }

    public void addFile(IFile file) throws FileNameExistsException {
        boolean ifTrue = files.stream().anyMatch(file1 -> file1.getFileName().equals(file.getFileName()));
        if (ifTrue){
            throw new FileNameExistsException(String.format("There is already a file named %s in the folder %s", file.getFileName(),folderName));
        }
        files.add(file);
    }

    @Override
    public String toString() {
        return "Folder{}";
    }
}

public class FileSystemTest {

    public static Folder readFolder (Scanner sc)  {

        Folder folder = new Folder(sc.nextLine());
        int totalFiles = Integer.parseInt(sc.nextLine());

        for (int i=0;i<totalFiles;i++) {
            String line = sc.nextLine();

            if (line.startsWith("0")) {
                String fileInfo = sc.nextLine();
                String [] parts = fileInfo.split("\\s+");
                try {
                    folder.addFile(new File(parts[0], Long.parseLong(parts[1])));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            }
            else {
                try {
                    folder.addFile(readFolder(sc));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return folder;
    }

    public static void main(String[] args)  {

        //file reading from input

        Scanner sc = new Scanner(System.in);

        System.out.println("===READING FILES FROM INPUT===");
        FileSystem fileSystem = new FileSystem();
        try {
            fileSystem.addFile(readFolder(sc));
        } catch (FileNameExistsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("===PRINTING FILE SYSTEM INFO===");
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING FILE SYSTEM INFO AFTER SORTING===");
        fileSystem.sortBySize();
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING THE SIZE OF THE LARGEST FILE IN THE FILE SYSTEM===");
        System.out.println(fileSystem.findLargestFile());




    }
}

class FileSystem{
    Folder root;

    public FileSystem() {
        this.root = new Folder("root");
    }


    public void addFile(IFile file) throws FileNameExistsException {
        root.addFile(file);
    }


    public long findLargestFile() {
        return root.findLargestFile();
    }

    public void sortBySize(){
        root.sortBySize();
    }

    @Override
    public String toString() {
        return root.getFileInfo("");
    }
}