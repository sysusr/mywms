/*
 * Copyright (c) 2006 - 2010 LinogistiX GmbH
 * 
 *  www.linogistix.com
 *  
 *  Project myWMS-LOS
 */
package de.linogistix.common.bobrowser.browse;

import de.linogistix.common.res.CommonBundleResolver;
import de.linogistix.common.gui.component.other.TopComponentExt;
import de.linogistix.common.userlogin.LoginService;
import de.linogistix.common.userlogin.LoginState;
import de.linogistix.common.util.ExceptionAnnotator;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ActionMap;
import javax.swing.JScrollPane;
import org.openide.ErrorManager;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 * Top component which displays something.
 */
final class BOBrowserTopComponent extends TopComponentExt implements ExplorerManager.Provider, Lookup.Provider{
  
  private static final Logger log = Logger.getLogger(BOBrowserTopComponent.class.getName());
  
  private static BOBrowserTopComponent instance;
  /** path to the icon used by the component and its open action */
  static final String ICON_PATH = "de/linogistix/bobrowser/res/icon/BOBrowser.png";
  
  private static final String PREFERRED_ID = "BOBrowserTopComponent";
  
  private final ExplorerManager mgr = new ExplorerManager();
  
  private BOBrowserNode.RootBONode rootNode;
  
  private BOBrowserTopComponent() {
    initComponents();
    setName(NbBundle.getMessage(CommonBundleResolver.class, "CTL_BOBrowserTopComponent"));
    setToolTipText(NbBundle.getMessage(CommonBundleResolver.class, "HINT_BOBrowserTopComponent"));
    setIcon(ImageUtilities.loadImage(ICON_PATH, true));
    BeanTreeView v  = new BeanTreeView();
    v.setRootVisible(false);
    jScrollPane1 = v;
    setLayout(new java.awt.BorderLayout());
    add(jScrollPane1, java.awt.BorderLayout.CENTER);
    ActionMap map = getActionMap();
    associateLookup(ExplorerUtils.createLookup(mgr, map));
      LoginService login = (LoginService)Lookup.getDefault().lookup(LoginService.class);
      login.addLoginStateChangeListener(new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent evt) {
          log.log(Level.INFO, "Got login state change: " + evt.getNewValue());
        }
      });
      //Thx to http://netbeans.dzone.com/news/secrets-netbeans-window-system
      putClientProperty("TopComponentAllowDockAnywhere", Boolean.TRUE);
      
  }
  
//  /**
//   * Select any node once to initialize BOLookup
//   */
//  private void initNodes(Node n){
//    log.info("*** init node " + n.getName());
//    try {
//      getExplorerManager().setSelectedNodes(new Node[]{n});
//      Children ch = n.getChildren();
//      for (Node node: ch.getNodes()){     
//        getExplorerManager().setSelectedNodes(new Node[]{node});
//      }
//      for (Node node: ch.getNodes()){     
//        initNodes(node);
//      }
//      
//    } catch (PropertyVetoException ex) {
//      log.log(Level.SEVERE,ex.getMessage(),ex);
//    }
//  }
  
  public ExplorerManager getExplorerManager() {
    return mgr;
  }
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
  private void initComponents() {

    setLayout(new java.awt.BorderLayout());

  }// </editor-fold>//GEN-END:initComponents
  
  JScrollPane jScrollPane1;
  // Variables declaration - do not modify//GEN-BEGIN:variables
  // End of variables declaration//GEN-END:variables
  
  /**
   * Gets default instance. Do not use directly: reserved for *.settings files only,
   * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
   * To obtain the singleton instance, use {@link findInstance}.
   */
  public static synchronized BOBrowserTopComponent getDefault() {
    if (instance == null) {
      instance = new BOBrowserTopComponent();
    }
    return instance;
  }
  
  /**
   * Obtain the BOBrowserTopComponent instance. Never call {@link #getDefault} directly!
   */
  public static synchronized BOBrowserTopComponent findInstance() {
    TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
    if (win == null) {
      //ErrorManager.getDefault().log(ErrorManager.WARNING, "Cannot find BOBrowser component. It will not be located properly in the window system.");
      return getDefault();
    }
    if (win instanceof BOBrowserTopComponent) {
      return (BOBrowserTopComponent)win;
    }
    ErrorManager.getDefault().log(ErrorManager.WARNING, "There seem to be multiple components with the '" + PREFERRED_ID + "' ID. That is a potential source of errors and unexpected behavior.");
    return getDefault();
  }
  
  public int getPersistenceType() {
    return TopComponent.PERSISTENCE_ALWAYS;
  }
  
  public void componentOpened() {
     try {    
      de.linogistix.common.userlogin.LoginService login = (LoginService) Lookup.getDefault().lookup(LoginService.class); 
      if (login.getState() != LoginState.AUTENTICATED) {
        return;
      }
      this.rootNode = new BOBrowserNode.RootBONode(); 
      getExplorerManager().setRootContext(rootNode);
      
    } catch (DataObjectNotFoundException ex) {
      ExceptionAnnotator.annotate(ex);
    }
  }
  
  public void componentClosed() {
    // TODO add custom code on component closing
  }
  
  protected void componentActivated() {
    ExplorerUtils.activateActions(getExplorerManager(), true);
  }
  protected void componentDeactivated() {
    ExplorerUtils.activateActions(getExplorerManager(), false);
  }
  
  /** replaces this in object stream */
  public Object writeReplace() {
    return new ResolvableHelper();
  }
  
  protected String preferredID() {
    return PREFERRED_ID;
  }
  
  public void open(){
      
      Mode mode = WindowManager.getDefault().findMode("explorer");
      
      if(mode != null){
          
          System.out.println("---- OPEN BOBrowser in EXPLORER");
          
          mode.dockInto(this);
      }
      
      super.open();
  }
  
  final static class ResolvableHelper implements Serializable {
    private static final long serialVersionUID = 1L;
    public Object readResolve() {
      return BOBrowserTopComponent.getDefault();
    }
  }
  
}
