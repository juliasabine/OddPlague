
package oddplague;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;


public class OddPlague extends JFrame{
    int numCell = 20;
    String cellsOn[][] = new String[numCell][numCell];
    String cellsOnNext[][] = new String[numCell][numCell];
    int cellWidth = 800/numCell;
    Random r = new Random();
    
    public void fillFirstGen(){ //all healthy
        for (int i = 0; i < cellsOn.length; i++) {
            for (int j = 0; j < cellsOn.length; j++) {
                cellsOn[i][j] = "healthy";
            }
        }
    }
    
    public int[] neighbours(int i, int j){
        int a[] = new int[4];
        if (i == 0){
            a[0] = i;
            a[1] = i + 1;
        }
        else if (i == numCell-1){
            a[0] = i - 1;
            a[1] = i;
        }
        else{
            a[0] = i - 1;
            a[1] = i + 1;
        }
        if (j == 0){
            a[2] = j;
            a[3] = j + 1;
        }
        else if (j == numCell-1){
            a[2] = j - 1;
            a[3] = j;
        }
        else{
            a[2] = j - 1;
            a[3] = j + 1;
        }
        return a;
    }
    
    public String healthyNext(int i, int j){
        int neighbour[] = neighbours(i, j);
        int sick = 0;
        int odds = r.nextInt(100);
        for (int k = neighbour[0]; k <= neighbour[1]; k++) {
            for (int l = neighbour[2]; l <= neighbour[3]; l++) {
                if (k != i && l != j){
                    if (cellsOn[k][l].equals("sick")){
                        sick ++;
                    }
                }
            }
        }
        if (sick > 2 || odds < 1){
            return "sick";
        }
        else{
            return "healthy";
        }
    }
    
    public String sickNext(){
        int odds = r.nextInt(100);
        if (odds < 5){
            return "immune";
        }
        else if (odds < 20){
            return "dead";
        }
        else{
            return "sick";
        }
    }
    
    public String immuneNext(int i, int j){
        int neighbour[] = neighbours(i, j);
        int ghost = 0;
        for (int k = neighbour[0]; k <= neighbour[1]; k++) {
            for (int l = neighbour[2]; l <= neighbour[3]; l++) {
                if (k != i || l != j){
                    if (cellsOn[k][l].equals("ghost")){
                        ghost ++;
                    }
                }
            }
        }
        if (ghost >= 1){
            return "crazy";
        }
        else{
            return "immune";
        }
    }
    
    public String crazyNext(int i, int j){
        int neighbour[] = neighbours(i, j);
        int crazy = 0;
        for (int k = neighbour[0]; k <= neighbour[1]; k++) {
            for (int l = neighbour[2]; l <= neighbour[3]; l++) {
                if (k != i && l != j){
                    if (cellsOn[k][l].equals("crazy")){
                        crazy ++;
                    }
                }
            }
        }
        int odds = r.nextInt(100);
        if (odds < crazy*10){
            return "dead";
        }
        else{
            return "crazy";
        }
    }
    
    public String deadNext(){
        int odds = r.nextInt(100);
        if (odds < 10){
            return "ghost";
        }
        else{
            return "dead";
        }
    }
    
    public void fillNextGen(){
        for (int i = 0; i < cellsOn.length; i++) {
            for (int j = 0; j < cellsOn.length; j++) {
                if (cellsOn[i][j].equals("healthy")){
                    cellsOnNext[i][j] = healthyNext(i,j);
                }
                else if (cellsOn[i][j].equals("sick")){
                    cellsOnNext[i][j] = sickNext();
                }
                else if (cellsOn[i][j].equals("immune")){
                    cellsOnNext[i][j] = immuneNext(i, j);
                }
                else if (cellsOn[i][j].equals("crazy")){
                    cellsOnNext[i][j] = crazyNext(i, j);
                }
                else if (cellsOn[i][j].equals("dead")){
                    cellsOnNext[i][j] = deadNext();
                }
                else{
                    cellsOnNext[i][j] = "ghost";
                }
            }
        }
    }
    
    public void overwriteCellsOn(){
        for (int i = 0; i < cellsOn.length; i++) {
            for (int j = 0; j < cellsOn.length; j++) {
                cellsOn[i][j] = cellsOnNext[i][j];
            }
        }
    }
    
    public void paint( Graphics g) {
        Image img = getImage();
        g.drawImage(img, 0, 0, rootPane);
    }
    
    public Image getImage() {
       BufferedImage bi = new BufferedImage(1000,1000,BufferedImage.TYPE_INT_RGB);
       
       Graphics2D g2 = (Graphics2D) bi.getGraphics();
       
       g2.setColor(Color.blue); 
        g2.fillRect(0, 0, 800, 800);
        int xPos = 0;
     
        for (int i = 0; i < cellsOn.length; i++) {
            int yPos = 0;
            for (int j = 0; j < cellsOn[0].length; j++){
                if (cellsOn[i][j].equals("healthy")) {
                    g2.setColor( Color.green );
                } 
                else if (cellsOn[i][j].equals("sick")){
                    g2.setColor(Color.gray);
                    //g2.setColor( Color.getHSBColor(50, 16, 45));
                }
                else if(cellsOn[i][j].equals("immune")){
                    g2.setColor(Color.yellow);
                }
                else if(cellsOn[i][j].equals("crazy")){
                    g2.setColor(Color.red);
                }
                else if(cellsOn[i][j].equals("ghost")){
                    g2.setColor(Color.white);
                }
                else{
                    g2.setColor(Color.black);
                }
                g2.fillRect(xPos, yPos, cellWidth, cellWidth);
//                g.setColor( Color.black );
//                g.drawRect(xPos, yPos, cellWidth, cellWidth);
                yPos += cellWidth;
            }
            xPos += cellWidth;           
        }
       
       return bi;
       
   }

    public void sleep( int numMilliseconds ) {
        try {
            Thread.sleep( numMilliseconds );
        } 
        
        catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        OddPlague op = new OddPlague();
	op.setTitle("My GOL");
	op.setBackground(  Color.black  );
        op.setSize(800, 800);
        op.setDefaultCloseOperation(EXIT_ON_CLOSE);
	op.fillFirstGen ();  //fills cellsOn for the 1st time using a file or a pattern
	op.setVisible( true );  //calls paint() for the 1st time
	for( int i = 0;  i <= 500; i++) {
		op.fillNextGen();  //fills cellsOnNext
		op.overwriteCellsOn();
		op.repaint();  //calls the paint() method for the 2nd, 3rd, etc. time.  
                op.sleep( 1000 );
        }
    }
}
