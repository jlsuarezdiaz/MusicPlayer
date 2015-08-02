/*
 * Author: Juan Luis Suárez Díaz
 * July, 2015
 * Music Player
 */
package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JToggleButton;

/**
 * Class RatingPanel.
 * A view for making ratings.
 * @author Juan Luis
 */
public class RatingPanel extends javax.swing.JPanel {
    /**
     * Rating.
     */
    private int rating;
    
    /**
     * Stars buttons.
     */
    private ArrayList<JToggleButton> stars;
    
    private void turnOn(JToggleButton star){
        star.setSelected(true);
        star.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/star_on.png")));
    }
    
    private void turnOff(JToggleButton star){
        star.setSelected(false);
        star.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/star_off.png")));
    }
    
    private void turn(JToggleButton star, boolean on){
        if(on) turnOn(star);
        else turnOff(star);
    }
    
    /**
     * Creates new form RatingPanel
     */
    public RatingPanel() {
        initComponents();
        rating = 0;
        
        stars = new ArrayList();
        stars.add(star1);
        stars.add(star2);
        stars.add(star3);
        stars.add(star4);
        stars.add(star5);
        stars.add(star6);
        
        for(JToggleButton s: stars){
            s.addActionListener((ActionEvent evt) -> {
                starActionPerformed(evt,s);
            });
        }
    }
    
    /**
     * Gets rating.
     * @return rating.
     */
    public int getRating(){
        return rating;
    }
    
    /**
     * Sets rating.
     * @param rating rating.
     */
    public void setRating(int rating){
        if(rating <= 6) this.rating = rating;
        for(int i = 1; i <= rating; i++){
            turnOn(stars.get(i-1));
        }
    }
    
    private void starActionPerformed(java.awt.event.ActionEvent evt,JToggleButton star){
        int i = stars.indexOf(star);
        for(int j = 0; j <= i; j++){
            turnOn(stars.get(j));
        }
        for(int j = i+1; j < stars.size(); j++){
            turnOff(stars.get(j));
        }
        rating = i+1;
    }
    
    /**
     * Adds mouse listeners for every star button.
     * @param evt Mouse event to add.
     */
    public void addMouseListener(MouseAdapter evt){
        for(JToggleButton s : stars){
            s.addMouseListener(evt);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        star1 = new javax.swing.JToggleButton();
        star6 = new javax.swing.JToggleButton();
        star2 = new javax.swing.JToggleButton();
        star3 = new javax.swing.JToggleButton();
        star4 = new javax.swing.JToggleButton();
        star5 = new javax.swing.JToggleButton();

        star1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/star_off.png"))); // NOI18N
        star1.setBorder(null);
        star1.setBorderPainted(false);
        star1.setMaximumSize(new java.awt.Dimension(28, 28));
        star1.setMinimumSize(new java.awt.Dimension(28, 28));
        star1.setPreferredSize(new java.awt.Dimension(28, 28));

        star6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/star_off.png"))); // NOI18N
        star6.setBorder(null);
        star6.setBorderPainted(false);
        star6.setMaximumSize(new java.awt.Dimension(28, 28));
        star6.setMinimumSize(new java.awt.Dimension(28, 28));
        star6.setPreferredSize(new java.awt.Dimension(28, 28));

        star2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/star_off.png"))); // NOI18N
        star2.setBorder(null);
        star2.setBorderPainted(false);
        star2.setMaximumSize(new java.awt.Dimension(28, 28));
        star2.setMinimumSize(new java.awt.Dimension(28, 28));
        star2.setPreferredSize(new java.awt.Dimension(28, 28));

        star3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/star_off.png"))); // NOI18N
        star3.setBorder(null);
        star3.setBorderPainted(false);
        star3.setMaximumSize(new java.awt.Dimension(28, 28));
        star3.setMinimumSize(new java.awt.Dimension(28, 28));
        star3.setPreferredSize(new java.awt.Dimension(28, 28));

        star4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/star_off.png"))); // NOI18N
        star4.setBorder(null);
        star4.setBorderPainted(false);
        star4.setMaximumSize(new java.awt.Dimension(28, 28));
        star4.setMinimumSize(new java.awt.Dimension(28, 28));
        star4.setPreferredSize(new java.awt.Dimension(28, 28));

        star5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/star_off.png"))); // NOI18N
        star5.setBorder(null);
        star5.setBorderPainted(false);
        star5.setMaximumSize(new java.awt.Dimension(28, 28));
        star5.setMinimumSize(new java.awt.Dimension(28, 28));
        star5.setPreferredSize(new java.awt.Dimension(28, 28));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(star1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(star2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(star3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(star4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(star5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(star6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(star1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(star2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(star3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(star4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(star5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(star6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton star1;
    private javax.swing.JToggleButton star2;
    private javax.swing.JToggleButton star3;
    private javax.swing.JToggleButton star4;
    private javax.swing.JToggleButton star5;
    private javax.swing.JToggleButton star6;
    // End of variables declaration//GEN-END:variables
}
