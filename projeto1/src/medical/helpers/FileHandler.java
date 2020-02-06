package medical.helpers;

import java.io.File;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author thalysonalexr
 * @author Aldo Riboli
 */
public class FileHandler {

    private final String file;
    
    public FileHandler(String file) {
        this.file = file;
    }
    
    public <E> ArrayList<E> readFile(ArrayList<E> data) {
        
        File f = new File(this.file);
        
        if (f.exists() && !f.isDirectory()) {
     
            FileInputStream fin;
            ObjectInputStream oin;

            try {
                fin = new FileInputStream(this.file);
                oin = new ObjectInputStream(fin);
                E object;
                
                int size = oin.readInt();
                
                for (int i = 0; i < size; i++) {
                    object = (E) oin.readObject();
                    data.add(object);
                }

                oin.close();
                fin.close();
            } catch (FileNotFoundException e) {
                System.out.println("[Erro] Arquivo " + this.file + " nao encontrado.");
            } catch (EOFException e) {
                System.out.println("[Erro] Final do arquivo inesperado.");
            } catch (IOException e) {
                System.out.println("[Erro] Houve um erro durante a leitura.");
            } catch (ClassNotFoundException e) {
                System.out.println("[Erro] Nao foi possivel interpretar conteudo do arquivo.");
            }

            return data;
        }
        
        return new ArrayList<>();
    }
    
    public <E> boolean writeFile(ArrayList<E> data) {
        
        FileOutputStream fout;

        try {
            fout = new FileOutputStream(this.file);
            ObjectOutputStream oout = new ObjectOutputStream(fout);
            
            oout.writeInt(data.size());

            for (E object: data)
                oout.writeObject(object);

            oout.close();
            fout.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }
}