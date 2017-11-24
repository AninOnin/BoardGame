
package mcfothelloai;

/**
 *
 * @author mfrost
 */
public class Move {
    int r;
    int c;
    int value;
    
    public Move(int row, int column) {
        r = row;
        c = column;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.r;
        hash = 97 * hash + this.c;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Move other = (Move) obj;
        if (this.r != other.r) {
            return false;
        }
        if (this.c != other.c) {
            return false;
        }
        return true;
    }
    
    public int getR(){
        return r;
    }
    
    public int getC(){
        return c;
    }
    
    
    
    /*    
    public boolean equals(Object other){
        
    }
    */
    public boolean isPass(){
        return r==0 && c == 0;
    }
    
    @Override
    public String toString() {
        return (c + " " + r);
    }
    
    
    
}//Move
