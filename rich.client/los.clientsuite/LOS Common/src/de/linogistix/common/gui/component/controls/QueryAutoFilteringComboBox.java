/*
 * Copyright (c) 2006 - 2012 LinogistiX GmbH
 * 
 *  www.linogistix.com
 *  
 *  Project myWMS-LOS
 */
package de.linogistix.common.gui.component.controls;

import de.linogistix.common.bobrowser.api.BOLookup;
import de.linogistix.common.bobrowser.bo.BO;
import de.linogistix.common.gui.component.other.TopComponentExt;
import de.linogistix.common.gui.object.IconType;
import de.linogistix.common.res.CommonBundleResolver;
import de.linogistix.common.util.ExceptionAnnotator;
import de.linogistix.los.query.BusinessObjectQueryRemote;
import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.mywms.model.BasicEntity;
import org.mywms.model.Client;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

/**
 * A Component for autofiltering text columns in a database. You have to provide
 * business entity class in {@link #boClass} and {@link searchProperty}
 *
 * @author trautm
 */
public class QueryAutoFilteringComboBox extends JPanel implements AutoFilteringComboBoxListener  {

    private final static Logger log = Logger.getLogger(QueryAutoFilteringComboBox.class.getName());

    public final static String ITEM_CHANGED = "ItemChanged";

    private Class boClass;
    private BusinessObjectQueryRemote queryRemote;
    private Client client;
    private String searchProperty = null;
    private int maxResult = 25;
    private List<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();
    private boolean mandatory = false;
    private Object[] params;

    public QueryAutoFilteringComboBox() {
        initComponents();
        getAutoFilteringComboBox().addSearchListener(this);
    }

    /** Creates new form AbstractBOEditor */
    public QueryAutoFilteringComboBox(Class boClass) {
        this();
        setBoClass(boClass);
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        editorLabel = new de.linogistix.common.gui.component.controls.LosLabel();
        autoFilteringComboBox = new AutoFilteringComboBox();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
        setFocusable(false);
        setLayout(new java.awt.GridBagLayout());

        editorLabel.setText("-");
        editorLabel.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(editorLabel, gridBagConstraints);

        autoFilteringComboBox.setEditable(true);
        autoFilteringComboBox.setMinimumSize(new java.awt.Dimension(24, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        add(autoFilteringComboBox, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JComboBox autoFilteringComboBox;
    private javax.swing.JLabel editorLabel;
    // End of variables declaration//GEN-END:variables

    public AutoFilteringComboBox getAutoFilteringComboBox(){
        return (AutoFilteringComboBox) autoFilteringComboBox;
    }
    
    public LosLabel getEditorLabel(){
        return (LosLabel) editorLabel;
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        autoFilteringComboBox.setEnabled(enabled);
    }
    
    public void setEditable(boolean editable){
        autoFilteringComboBox.setEditable(editable);
    }
    
    public boolean isEditable(){
        return autoFilteringComboBox.isEditable();
    }
    
    public int getColumns() {
        return getAutoFilteringComboBox().getColumns();
    }
    public void setColumns(int columns) {
        getAutoFilteringComboBox().setColumns(columns);
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        getEditorLabel().setShowMandatoryFlag(mandatory);
        this.mandatory=mandatory;
    }
    
    public boolean checkSanity(){
        if(getAutoFilteringComboBox().getText().length() == 0){
            if (isMandatory()){
                showEmptyErrorMessage();
                return false;
            }
        } else{
            getEditorLabel().setText();
        }
        return true;
    }
    public void showEmptyErrorMessage(){
        getEditorLabel().setText(NbBundle.getMessage(CommonBundleResolver.class, "Empty field"), IconType.ERROR);
    }

    @Override
    public void requestFocus() {
        autoFilteringComboBox.requestFocus();
    }

    @Override
    public boolean requestFocusInWindow() {
        return autoFilteringComboBox.requestFocusInWindow();
    }

    @Override
    public Font getFont(){
        if (autoFilteringComboBox != null)
            return autoFilteringComboBox.getFont();
        else
            return super.getFont();
    }

    @Override
    public void setFont(Font font){
        if (autoFilteringComboBox != null)
            autoFilteringComboBox.setFont(font);
    }


     /**
      * Make sure to call this method AFTER {@link  BOLookup} is initialized. A
      * good place is {@link TopComponentExt#componentOpened()}. Also, mkae sure to call
      * {@link #setBoClass(java.lang.Class) } before!
      */
    public void initAutofiltering() {
        if (this.getBoClass() == null){
            throw new NullPointerException("boClass must not be null! Set before adding to a panel.");
        }

        BOLookup l = (BOLookup) Lookup.getDefault().lookup(BOLookup.class);
        BO bo = (BO) l.lookup(boClass);
        if( bo == null ) {
            log.severe("Cannot lookup BOClass: " + boClass.getCanonicalName());
            return;
        }

        this.queryRemote = bo.getQueryService();
        clear();
        setEditorLabelText();
    }

    public void setEditorLabelTitle(String text) {
        getEditorLabel().setTitleText(text);
    }

    public void setEditorLabelText() {
        getEditorLabel().setText();
    }

    public void requestProposalData( String searchString ) {
        List<String> items = null;

        getAutoFilteringComboBox().removeAllItems();

        try {
            if (this.params == null){
                items = (List<String>) this.queryRemote.autoCompletionStringProperty(client, searchProperty, searchString, maxResult );
            } else{
                items = (List<String>) this.queryRemote.autoCompletionStringProperty(client, searchProperty, searchString, maxResult, params );
            }

        } catch (Throwable ex) {
            ExceptionAnnotator.annotate(ex);
        }

        List<Object> itemList = new ArrayList();
        if (items != null) {
            for (String s : items) {
                itemList.add(s);
            }
        }
        getAutoFilteringComboBox().setProposalList(itemList);

    }

    public void clear() {
        getAutoFilteringComboBox().setText("");
        getAutoFilteringComboBox().removeAllItems();
        getEditorLabel().setText();
        setClient(null);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BusinessObjectQueryRemote getQueryRemote() {
        return queryRemote;
    }

    public void setQueryRemote(BusinessObjectQueryRemote queryRemote) {
        this.queryRemote = queryRemote;
    }


    public Class getBoClass() {
        return boClass;
    }
    public void setBoClass(Class boClass) {
        this.boClass = boClass;
    }

    /**
     * @return searchProperty the searchProperty (name of the property of a {@link BasicEntity}) to search for
     */
    public String getSearchProperty() {
        return searchProperty;
    }

    /**
     * @param searchProperty the searchProperty (name of the property of a {@link BasicEntity}) to search for
     */
    public void setSearchProperty(String searchProperty) {
        this.searchProperty = searchProperty;
    }

    public String getText() {
        return getAutoFilteringComboBox().getText();
    }

    public void setText(String s) {
        getAutoFilteringComboBox().setText(s);
    }

    public void setPreferredComboBoxSize(Dimension preferredSize) {
        getAutoFilteringComboBox().setPreferredSize(preferredSize);
    }

    public Dimension getPreferredComboBoxSize() {
        return getAutoFilteringComboBox().getPreferredSize();
    }

    public void addItemChangeListener(PropertyChangeListener l) {
        if(!listeners.contains(l))
            listeners.add(l);
    }
    public void removeItemChangedListener(PropertyChangeListener l) {
        listeners.remove(l);
    }

    public boolean selectionChanged() {
        PropertyChangeEvent e = new PropertyChangeEvent(this, ITEM_CHANGED, null, getText().trim());
        for (PropertyChangeListener p : listeners) {
            p.propertyChange(e);
        }
        return true;
    }

    public void setParams(Object[] params){
        this.params = params;
    }

    public Object[] getParams(){
        return this.params;
    }

}
