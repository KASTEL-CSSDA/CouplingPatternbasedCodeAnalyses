package com.sdq.coupling.ui;

import com.sdq.coupling.CouplingAnalysis;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Very basic user interface to select the model folder and 
 * jar file for analysis.
 *
 * @author Laura
 *
 */
public class UserInterface {

  private JFrame frame;

  public UserInterface() {
    frame = new JFrame("SDQ - Coupling Analysis");

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(600, 170);
    frame.setResizable(false);

    // parent content pane
    JPanel contentPane = new JPanel();
    Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
    contentPane.setBorder(padding);
    frame.setContentPane(contentPane);
    contentPane.setLayout(new GridLayout(3, 1, 15, 10));

    // model select dialog
    Container modelContainer = new Container();
    modelContainer.setLayout(new GridLayout(0, 3, 5, 0));
    final JLabel labelSelectModel = new JLabel("Model File:");
    final JTextField textFieldModel = new JTextField("./example/model/");
    final JButton buttonSelectModel = new JButton("Select");
    buttonSelectModel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
          textFieldModel.setText(chooser.getSelectedFile().getPath());
        }
      }
    });
    modelContainer.add(labelSelectModel);
    modelContainer.add(textFieldModel);
    modelContainer.add(buttonSelectModel);

    // source folder dialog

    // jar file dialog
    Container jarContainer = new Container();
    jarContainer.setLayout(new GridLayout(0, 3, 5, 0));
    final JLabel labelSelectJar = new JLabel("JAR File:");
    final JTextField textFieldJar = new JTextField("./example/example.jar");
    final JButton buttonSelectJar = new JButton("Select");
    buttonSelectJar.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = 
            new FileNameExtensionFilter("Compiled JAR File (*.jar)", "jar");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
          textFieldJar.setText(chooser.getSelectedFile().getPath());
        }
      }
    });
    jarContainer.add(labelSelectJar);
    jarContainer.add(textFieldJar);
    jarContainer.add(buttonSelectJar);

    // run button
    final JButton runButton = new JButton("Run Coupling Analysis");
    runButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        CouplingAnalysis.run(textFieldModel.getText(), textFieldJar.getText());
      }
    });

    // add all ui elements
    contentPane.add(modelContainer);
    contentPane.add(jarContainer);
    contentPane.add(runButton);

    // make frame appear centered on screen

  }

  public void show() {
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
