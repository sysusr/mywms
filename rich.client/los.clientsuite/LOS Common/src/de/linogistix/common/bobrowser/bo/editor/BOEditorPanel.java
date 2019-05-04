/*
 * Copyright (c) 2006 - 2012 LinogistiX GmbH
 * 
 *  www.linogistix.com
 *  
 *  Project myWMS-LOS
 */
package de.linogistix.common.bobrowser.bo.editor;

import de.linogistix.common.bobrowser.bo.*;
import org.openide.explorer.propertysheet.PropertySheet;
import org.openide.nodes.Node;

/**
 *
 * @author  trautm
 */
public class BOEditorPanel extends javax.swing.JPanel {
  
  private BOEntityNodeReadOnly node;
  
  /** Creates new form BOEditorPanel */
  public BOEditorPanel(BOEntityNodeReadOnly node) {
    this.node = node;
    initComponents();
    initHeader();
    initEditor();
  }
  
  Class getBundleResolver(){
    return node.getBundleResolver();
  }
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    headerPanel = new javax.swing.JPanel();
    editorPanel = new javax.swing.JPanel();

    setLayout(new java.awt.BorderLayout());

    headerPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
    add(headerPanel, java.awt.BorderLayout.NORTH);

    editorPanel.setPreferredSize(new java.awt.Dimension(400, 300));
    editorPanel.setLayout(new java.awt.BorderLayout());
    add(editorPanel, java.awt.BorderLayout.CENTER);
  }// </editor-fold>//GEN-END:initComponents
  
  private void initHeader() {
    BOEditorHeader h = new BOEditorHeader(node);
    headerPanel.add(h);
  }
  
  protected void initEditor() {     
        PropertySheet detailView;
        detailView = new PropertySheet();
        detailView.setNodes(new Node[]{node});
        editorPanel.add(detailView);
  }
  
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JPanel editorPanel;
  private javax.swing.JPanel headerPanel;
  // End of variables declaration//GEN-END:variables
  
}
