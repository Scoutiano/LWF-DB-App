/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lwf;

import java.io.IOException;
import java.sql.Connection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 *
 * @author Mohanad
 */
public class FXMLDocumentController implements Initializable {

    private Connection con;
    private String dbURL;
    private String dbUsername = "root";
    private String dbPassword = "6666";
    private String URL = "127.0.0.1";
    private String port = "3306";
    private String dbName = "LWF";

    //Set up components for the student admission form
    static Stage studentFormStage = new Stage();
    static Stage professionFormStage = new Stage();
    TextField studentNameTF = new TextField();
    Label studentNameLbl = new Label("Name:");
    TextField studentGuardianNameTF = new TextField();
    Label studentGuardianNameLbl = new Label("Guardian Name:");
    TextField studentNationalIDTF = new TextField();
    Label studentNationalIDLbl = new Label("National ID:");
    TextField studentAddressTF = new TextField();
    Label studentAddressLbl = new Label("Address:");
    TextField studentContact1TF = new TextField();
    Label studentContact1Lbl = new Label("Contact 1:");
    TextField studentContact2TF = new TextField();
    Label studentContact2Lbl = new Label("Contact 2:");
    DatePicker studentDateOfBirthDP = new DatePicker();
    Label studentDateOfBirthLbl = new Label("Date Of Birth:");
    ChoiceBox<String> studentProfessionCB = new ChoiceBox();
    Label studentProfessionLbl = new Label("Profession:");
    ChoiceBox<String> studentGenderCB = new ChoiceBox(FXCollections.observableArrayList("Male", "Female"));
    Label studentGenderLbl = new Label("Gender:");
    TextField studentIDTF = new TextField();
    Label studentIDLbl = new Label("ID:");
    Button searchStudentBtn = new Button("Search");

    Button admitStudentBtn = new Button("Admit Student");
    Button editStudentBtn = new Button("Edit Student");

    //Set up components for the student admission form
    static Stage employeeFormStage = new Stage();
    TextField employeeNameTF = new TextField();
    Label employeeNameLbl = new Label("Name:");
    TextField employeeNationalIDTF = new TextField();
    Label employeeNationalIDLbl = new Label("National ID:");
    TextField employeeAddressTF = new TextField();
    Label employeeAddressLbl = new Label("Address:");
    TextField employeeContact1TF = new TextField();
    Label employeeContact1Lbl = new Label("Contact 1:");
    TextField employeeContact2TF = new TextField();
    Label employeeContact2Lbl = new Label("Contact 2:");
    DatePicker employeeDateOfBirthDP = new DatePicker();
    Label employeeDateOfBirthLbl = new Label("Date Of Birth:");
    TextField employeeIDTF = new TextField();
    Label employeeIDLbl = new Label("ID:");
    ChoiceBox<String> employeeGenderCB = new ChoiceBox(FXCollections.observableArrayList("Male", "Female"));
    Label employeeGenderLbl = new Label("Gender:");
    Label employeeNotifLbl = new Label("");
    Button admitEmployeeBtn = new Button("Admit Employee");
    Button editEmployeeBtn = new Button("Edit Employee");
    Button searchEmployeeBtn = new Button("Search");

    @FXML
    Button setEmployeeTypeBtn = new Button();
    @FXML
    ChoiceBox<String> employeeTypeCB = new ChoiceBox();

    ToggleGroup employeeTypeTG = new ToggleGroup();
    @FXML
    RadioButton employeeTypeRB = new RadioButton();
    @FXML
    RadioButton teacherTypeRB = new RadioButton();
    @FXML
    RadioButton managerTypeRB = new RadioButton();
    @FXML
    RadioButton secretaryTypeRB = new RadioButton();

    Label studentNotifLbl = new Label();

    @FXML
    TableView<String[]> professionTable = new TableView<>();
    @FXML
    TableView<String[]> studentTable = new TableView<>();
    @FXML
    TableView<String[]> professionSubjectsTable = new TableView<>();
    @FXML
    Label professionNotifLbl = new Label();
    @FXML
    ChoiceBox<String> subjectCB = new ChoiceBox<>();
    ObservableList<String> subjectsOL;

    ObservableList<String> professionsOL;

    //Profession form
    TextField professionNameTF = new TextField();
    Label requestLbl = new Label();
    Button addProfessionBtn = new Button();

    //Tuition form
    Stage tuitionFormStage = new Stage();
    TextField payAmountTF = new TextField();
    Label amountPaidLbl = new Label("Amount paid:");
    Label amountRequiredLbl = new Label("Amount Required");
    Label paidLbl = new Label("");
    Label requiredLbl = new Label("");
    Button payBtn = new Button("Pay");
    Label tuitionNotifLbl = new Label("");

    //Subject form
    Stage subjectFormStage = new Stage();
    Label subjectNameLbl = new Label("Subject Name:");
    Label semesterLbl = new Label("Semester:");
    Label totalGradeLbl = new Label("Total Grade:");
    TextField subjectNameTF = new TextField();
    TextField totalGradeTF = new TextField();
    Button addSubjectBtn = new Button();
    ComboBox semesterCB = new ComboBox();
    ObservableList<String> semestersOL = FXCollections.observableArrayList("1", "2");
    Label subjectNotifLbl = new Label("");
    ComboBox studentYearCB = new ComboBox();
    Label studentYearLbl = new Label("Year:");

    //Toggle group for subjects to view students/teachers of selected subject
    ToggleGroup subjectInfoTG = new ToggleGroup();
    @FXML
    RadioButton studentRB = new RadioButton();
    @FXML
    RadioButton teacherRB = new RadioButton();

    ObservableList<String> yearOL = FXCollections.observableArrayList();
    @FXML
    ComboBox subjectYearCB = new ComboBox(yearOL);

    @FXML
    Button setGradeBtn = new Button("Set Grade");
    @FXML
    TextField setGradeTF = new TextField();
    @FXML
    Label setGradeNotifLbl = new Label("");
    @FXML
    Label subjectAverageLbl = new Label();
    @FXML
    Label subjectNumberOfStudentsLbl = new Label();

    @FXML
    Label avgLbl = new Label();
    @FXML
    Label numLbl = new Label();

    //Subjects Table
    @FXML
    TableView<String[]> subjectTable = new TableView<>();
    @FXML
    TableView<String[]> subjectInfoTable = new TableView<>();

    //Add a Teacher for the selected Subject from
    Stage addTeacherToSubjectStage = new Stage();
    TableView<String[]> addTeacherToSubjectTable = new TableView<>();
    Button addTeacherToSubjectBtn = new Button("Add Teacher");
    @FXML
    Button addTeacherBtn = new Button();
    @FXML
    Button removeTeacherBtn = new Button();

    //Employee table
    @FXML
    TableView<String[]> employeeTable = new TableView<>();

    @FXML
    ComboBox studentYearSelectCB = new ComboBox();

    @FXML
    TabPane tabPane = new TabPane();

    //studentGradesStage and its components
    Stage studentGradesStage = new Stage();
    TableView<String[]> studentGradesTable = new TableView<>();
    Label semester1Lbl = new Label();
    Label semester2Lbl = new Label();
    Label totalLbl = new Label();
    Label semester1AverageLbl = new Label();
    Label semester2AverageLbl = new Label();
    Label totalAverageLbl = new Label();

    //tuitionReportStage and its components
    
    Stage tuitionReportStage = new Stage();
    Label unpaidTableLbl = new Label("Students with debt");
    TableView<String[]> unpaidTable  = new TableView<>();
    Label expectedLbl  = new Label();
    Label paymentLbl  = new Label();
    Label expectedAmountLbl  = new Label();
    Label paidAmountLbl  = new Label();
    Label requirementLbl = new Label();
    Label requiredAmountLbl = new Label();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            loadStudentYears();

            employeeTypeCB.setItems(FXCollections.observableArrayList("Manager", "Teacher", "Secretary"));

            employeeTypeRB.setToggleGroup(employeeTypeTG);
            employeeTypeRB.setOnAction(e -> {
                try {

                    employeeTypeCB.setVisible(true);
                    setEmployeeTypeBtn.setVisible(true);
                    loadEmployees();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            });
            employeeTypeRB.setSelected(true);
            teacherTypeRB.setToggleGroup(employeeTypeTG);
            teacherTypeRB.setOnAction(e -> {
                try {

                    employeeTypeCB.setVisible(false);
                    setEmployeeTypeBtn.setVisible(false);
                    loadTeachers();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            });
            secretaryTypeRB.setToggleGroup(employeeTypeTG);
            secretaryTypeRB.setOnAction(e -> {
                try {

                    employeeTypeCB.setVisible(false);
                    setEmployeeTypeBtn.setVisible(false);
                    loadSecretaries();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            });
            managerTypeRB.setToggleGroup(employeeTypeTG);
            managerTypeRB.setOnAction(e -> {
                try {

                    employeeTypeCB.setVisible(false);
                    setEmployeeTypeBtn.setVisible(false);
                    loadManagers();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            });

            studentRB.setToggleGroup(subjectInfoTG);
            studentRB.setOnAction(e -> {
                try {

                    removeTeacherBtn.setVisible(false);
                    addTeacherBtn.setVisible(false);
                    setGradeBtn.setVisible(true);
                    setGradeTF.setVisible(true);
                    subjectYearCB.setVisible(true);
                    subjectAverageLbl.setVisible(true);
                    subjectNumberOfStudentsLbl.setVisible(true);
                    avgLbl.setVisible(true);
                    numLbl.setVisible(true);

                    String id = getSubjectID(subjectTable.getSelectionModel().getSelectedItem()[0]);
                    String queryCount = "select count(*) from enroll e, student s where e.student_id = s.student_id AND e.subject_id = " + id + ";";
                    String query = "select s.Student_ID, s.Name, e.Grade from enroll e, student s where e.student_id = s.student_id AND e.subject_id = " + id + ";";
                    
                    filterSubjectStudents();
                    
                    //loadQuery(query, queryCount, subjectInfoTable, 140);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

            });
            teacherRB.setToggleGroup(subjectInfoTG);
            teacherRB.setOnAction(e -> {
                try {

                    removeTeacherBtn.setVisible(true);
                    addTeacherBtn.setVisible(true);
                    setGradeBtn.setVisible(false);
                    setGradeTF.setVisible(false);
                    subjectYearCB.setVisible(false);
                    subjectAverageLbl.setVisible(false);
                    subjectNumberOfStudentsLbl.setVisible(false);
                    avgLbl.setVisible(false);
                    numLbl.setVisible(false);

                    String id = getSubjectID(subjectTable.getSelectionModel().getSelectedItem()[0]);
                    String queryCount = "select count(*) from teaching te,teacher t where te.teacher_ID = t.teacher_ID AND te.subject_id = " + id + ";";
                    String query = "select Employee_ID, Name from teaching te,employee t where te.teacher_ID = t.employee_ID AND te.subject_id = " + id + ";";

                    loadQuery(query, queryCount, subjectInfoTable, 210);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

            });
            studentRB.selectedProperty().set(true);

            //Load students from database
            professionsOL = FXCollections.observableArrayList();

            loadStudents();

            tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
                @Override
                public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {

                    try {
                        if (newTab.getText().compareTo("Professions") == 0) {

                            loadProfessionTable();
                            fillSubjectCB();

                        } else if (newTab.getText().compareTo("Students") == 0) {

                            loadStudentYears();
                            loadStudents();
                        } else if (newTab.getText().compareTo("Subjects") == 0) {

                            loadYears();
                            loadSubjects();
                        } else if (newTab.getText().compareTo("Employees") == 0) {
                            loadEmployees();
                        }
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }

                }
            });

            professionTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String[]>() {

                @Override
                public void changed(ObservableValue<? extends String[]> observable, String[] oldValue, String[] newValue) {
                    if (newValue == null) {
                        professionSubjectsTable.getColumns().clear();
                        return;
                    } else {

                        String id = "";
                        try {
                            id = getProfessionID(newValue[0]);
                            
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                        String queryCount = "select count(*) from ProfessionSubjects ps, Subject s where s.subject_ID = ps.subject_ID AND ps.profession_ID = '" + id + "';";
                        String query = "select s.Subject_ID,s.Name,s.Semester,s.Total_Grade from ProfessionSubjects ps, Subject s where s.subject_ID = ps.subject_ID AND ps.profession_ID = '" + id + "';";
                        try {
                            loadQuery(query, queryCount, professionSubjectsTable, 140);
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }

            });

            subjectTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String[]>() {

                @Override
                public void changed(ObservableValue<? extends String[]> observable, String[] oldValue, String[] newValue) {

                    if (newValue == null) {
                        subjectInfoTable.getColumns().clear();
                        return;
                    } else {
                        String id = "";
                        try {
                            id = getSubjectID(newValue[0]);
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                        String selected = ((RadioButton) subjectInfoTG.getSelectedToggle()).getText();

                        String query = "";
                        String queryCount = "";

                        try {
                            if (selected.compareTo("Teachers") == 0) {

                                queryCount = "select count(*) from teaching te,teacher t where te.teacher_ID = t.teacher_ID AND te.subject_id = " + id + ";";
                                query = "select Employee_ID, Name from teaching te,employee t where te.teacher_ID = t.employee_ID AND te.subject_id = " + id + ";";
                                loadQuery(query, queryCount, subjectInfoTable, 210);

                            } else {

                                filterSubjectStudents();
                            }
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }

                        try {
                            getSubjectAverage();
                            getSubjectNumberOfStudents();
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }

            });

            subjectYearCB.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                    try {
                        getSubjectAverage();
                        getSubjectNumberOfStudents();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            });

            studentYearSelectCB.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                    try {

                        loadStudents();

                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            });
            
            subjectYearCB.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                    try {

                        filterSubjectStudents();

                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            });

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String getProfessionID(String professionName) throws ClassNotFoundException, SQLException {

        //Establish connection to database
        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        String SQL = "Select Profession_ID from Profession where profession_name = '" + professionName + "'";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        rs.next();
        String id = rs.getString(1);

        rs.close();
        stmt.close();
        con.close();

        return id;

    }

    public String getProfessionName(String professionID) throws ClassNotFoundException, SQLException {

        //Establish connection to database
        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        //Get current list of professions and add it to studentProfessionCB
        String SQL = "select Profession_Name from Profession where Profession_ID = " + professionID + ";";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        rs.next();
        String professionName = rs.getString(1);

        rs.close();
        stmt.close();
        con.close();

        return professionName;

    }

    int cheapCount = 0;

    public void loadStudentForm() {
        AnchorPane.setTopAnchor(studentNameTF, 44.0);
        AnchorPane.setLeftAnchor(studentNameTF, 161.0);

        AnchorPane.setTopAnchor(studentNameLbl, 49.0);
        AnchorPane.setLeftAnchor(studentNameLbl, 40.0);

        AnchorPane.setTopAnchor(studentGuardianNameTF, 133.0);
        AnchorPane.setLeftAnchor(studentGuardianNameTF, 161.0);

        AnchorPane.setTopAnchor(studentGuardianNameLbl, 138.0);
        AnchorPane.setLeftAnchor(studentGuardianNameLbl, 40.0);

        AnchorPane.setTopAnchor(studentNationalIDTF, 226.0);
        AnchorPane.setLeftAnchor(studentNationalIDTF, 161.0);

        AnchorPane.setTopAnchor(studentNationalIDLbl, 231.0);
        AnchorPane.setLeftAnchor(studentNationalIDLbl, 40.0);

        AnchorPane.setTopAnchor(studentAddressTF, 319.0);
        AnchorPane.setLeftAnchor(studentAddressTF, 161.0);

        AnchorPane.setTopAnchor(studentAddressLbl, 324.0);
        AnchorPane.setLeftAnchor(studentAddressLbl, 40.0);

        AnchorPane.setTopAnchor(studentIDTF, 319.0);
        AnchorPane.setLeftAnchor(studentIDTF, 520.0);

        AnchorPane.setTopAnchor(studentIDLbl, 324.0);
        AnchorPane.setLeftAnchor(studentIDLbl, 486.0);

        AnchorPane.setTopAnchor(studentContact1TF, 381.0);
        AnchorPane.setLeftAnchor(studentContact1TF, 161.0);

        AnchorPane.setTopAnchor(studentContact1Lbl, 386.0);
        AnchorPane.setLeftAnchor(studentContact1Lbl, 40.0);

        AnchorPane.setTopAnchor(studentContact2TF, 412.0);
        AnchorPane.setLeftAnchor(studentContact2TF, 161.0);

        AnchorPane.setTopAnchor(studentContact2Lbl, 417.0);
        AnchorPane.setLeftAnchor(studentContact2Lbl, 40.0);

        AnchorPane.setTopAnchor(studentProfessionCB, 44.0);
        AnchorPane.setLeftAnchor(studentProfessionCB, 565.0);
        studentProfessionCB.setPrefWidth(150);

        AnchorPane.setTopAnchor(studentProfessionLbl, 49.0);
        AnchorPane.setLeftAnchor(studentProfessionLbl, 483.0);

        AnchorPane.setTopAnchor(studentProfessionCB, 44.0);
        AnchorPane.setLeftAnchor(studentProfessionCB, 565.0);
        studentProfessionCB.setPrefWidth(150);

        AnchorPane.setTopAnchor(studentProfessionLbl, 49.0);
        AnchorPane.setLeftAnchor(studentProfessionLbl, 483.0);

        AnchorPane.setTopAnchor(studentDateOfBirthDP, 226.0);
        AnchorPane.setLeftAnchor(studentDateOfBirthDP, 481.0);
        studentDateOfBirthDP.setPrefHeight(31);
        studentDateOfBirthDP.setPrefWidth(234);
        studentDateOfBirthDP.setEditable(false);

        AnchorPane.setTopAnchor(studentDateOfBirthLbl, 200.0);
        AnchorPane.setLeftAnchor(studentDateOfBirthLbl, 481.0);

        AnchorPane.setTopAnchor(studentGenderCB, 133.0);
        AnchorPane.setLeftAnchor(studentGenderCB, 565.0);

        AnchorPane.setTopAnchor(studentGenderLbl, 138.0);
        AnchorPane.setLeftAnchor(studentGenderLbl, 483.0);

        AnchorPane.setTopAnchor(studentYearCB, 88.0);
        AnchorPane.setLeftAnchor(studentYearCB, 565.0);

        AnchorPane.setTopAnchor(studentYearLbl, 88.0);
        AnchorPane.setLeftAnchor(studentYearLbl, 483.0);

        AnchorPane.setTopAnchor(studentNotifLbl, 443.0);
        AnchorPane.setLeftAnchor(studentNotifLbl, 487.0);

        AnchorPane.setTopAnchor(admitStudentBtn, 381.0);
        AnchorPane.setLeftAnchor(admitStudentBtn, 487.0);
        admitStudentBtn.setPrefHeight(61);
        admitStudentBtn.setPrefWidth(234);
        admitStudentBtn.setVisible(true);
        admitStudentBtn.setOnAction(e -> {
            try {
                admitStudent();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });

        AnchorPane.setTopAnchor(editStudentBtn, 381.0);
        AnchorPane.setLeftAnchor(editStudentBtn, 487.0);
        editStudentBtn.setPrefHeight(61);
        editStudentBtn.setPrefWidth(234);
        editStudentBtn.setVisible(false);
        editStudentBtn.setOnAction(e -> {
            try {
                editStudent();
            } catch (SQLException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        AnchorPane.setTopAnchor(searchStudentBtn, 381.0);
        AnchorPane.setLeftAnchor(searchStudentBtn, 487.0);
        searchStudentBtn.setPrefHeight(61);
        searchStudentBtn.setPrefWidth(234);
        searchStudentBtn.setVisible(true);
        searchStudentBtn.setOnAction(e -> {

            try {
                searchStudent();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        AnchorPane ap = new AnchorPane();
        ap.getChildren().addAll(studentYearCB, studentYearLbl,
                studentIDTF, studentIDLbl,
                studentNameTF, studentNameLbl,
                studentGuardianNameTF, studentGuardianNameLbl,
                studentNationalIDTF, studentNationalIDLbl,
                studentAddressTF, studentAddressLbl,
                studentContact1TF, studentContact1Lbl,
                studentContact2TF, studentContact2Lbl,
                studentProfessionCB, studentProfessionLbl,
                studentGenderCB, studentGenderLbl,
                studentDateOfBirthDP, studentDateOfBirthLbl,
                studentNotifLbl, admitStudentBtn,
                editStudentBtn, searchStudentBtn);

        Scene scene = new Scene(ap);
        studentFormStage.setScene(scene);
        studentFormStage.setHeight(530);
        studentFormStage.setWidth(750);
        studentFormStage.setResizable(false);
        studentFormStage.initOwner(LWF.stage.getScene().getWindow());
        studentFormStage.initModality(Modality.APPLICATION_MODAL);
        
        studentFormStage.getIcons().add(new Image(LWF.class.getResourceAsStream("logo.png")));

    }

    public void loadStudentProfessions() throws ClassNotFoundException, SQLException {
        professionsOL = FXCollections.observableArrayList();

        //Establish connection to database
        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        //Get current list of professions and add it to studentProfessionCB
        String SQL = "Select Profession_Name from Profession;";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        //Add professions to studentProfessionCB
        while (rs.next()) {
            professionsOL.add(rs.getString(1));
        }
        studentProfessionCB.setItems(professionsOL);

        //Close connection
        rs.close();
        stmt.close();
        con.close();
    }

    public void loadStudentAddForm() throws ClassNotFoundException, SQLException {

        loadStudentProfessions();

        if (cheapCount == 0) {
            loadStudentForm();
            cheapCount++;
        }

        studentYearCB.setVisible(false);
        studentYearLbl.setVisible(false);
        studentIDTF.setVisible(false);
        studentIDLbl.setVisible(false);
        searchStudentBtn.setVisible(false);
        admitStudentBtn.setVisible(true);
        editStudentBtn.setVisible(false);

        clearStudentForm();

        studentFormStage.setTitle("Student Admission Form");
        
        studentFormStage.show();
    }

    public void loadStudents() throws SQLException, ClassNotFoundException {
        String query = "Select * from Student where class_year = " + (String) (studentYearSelectCB.getSelectionModel().getSelectedItem()) + ";";
        String countQuery = "Select count(*) from Student where class_year = " + (String) (studentYearSelectCB.getSelectionModel().getSelectedItem()) + ";";
        loadStudentQuery(query, countQuery, studentTable, 140);
    }

    public void loadSubjects() throws SQLException, ClassNotFoundException {
        String query = "Select Name as Subject_Name,Semester,Total_Grade from Subject;";
        String countQuery = "Select count(*) from Subject;";
        loadQuery(query, countQuery, subjectTable, 140);
    }

    public void admitStudent() throws ClassNotFoundException, SQLException {

        String name = studentNameTF.getText().trim();
        if (name.compareTo("") == 0) {

            fadeTransition(studentNotifLbl, "Name must be filled in");
            return;
        }
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isLetter(name.charAt(i)) && name.charAt(i) != ' ') {

                fadeTransition(studentNotifLbl, "Name is invalid");
                return;
            }
        }

        String guardianName = studentGuardianNameTF.getText().trim();
        if (guardianName.compareTo("") == 0) {

            fadeTransition(studentNotifLbl, "Guardian Name must be filled in");
            return;
        }
        for (int i = 0; i < guardianName.length(); i++) {
            if (!Character.isLetter(guardianName.charAt(i)) && guardianName.charAt(i) != ' ') {

                fadeTransition(studentNotifLbl, "Guardian Name is invalid");
                return;
            }
        }

        String nationalID = studentNationalIDTF.getText().trim();
        if (nationalID.compareTo("") == 0) {

            fadeTransition(studentNotifLbl, "National ID must be filled in");
            return;
        }
        for (int i = 0; i < nationalID.length(); i++) {
            if (!Character.isDigit(nationalID.charAt(i))) {

                fadeTransition(studentNotifLbl, "National ID is invalid");
                return;
            }
        }

        String contact1 = studentContact1TF.getText().trim();
        if (contact1.compareTo("") == 0) {

            fadeTransition(studentNotifLbl, "Contact 1 must be filled in");
            return;
        }
        for (int i = 0; i < contact1.length(); i++) {
            if (!Character.isDigit(contact1.charAt(i))) {

                fadeTransition(studentNotifLbl, "Contact 1 is invalid");
                return;
            }
        }

        String contact2 = studentContact2TF.getText().trim();
        if (contact2.compareTo("") == 0) {
            contact2 = "0";
        }
        for (int i = 0; i < contact2.length(); i++) {
            if (!Character.isDigit(contact2.charAt(i))) {

                fadeTransition(studentNotifLbl, "Contact 2 is invalid");
                return;
            }
        }

        String address = studentAddressTF.getText().trim();
        if (address.compareTo("") == 0) {

            fadeTransition(studentNotifLbl, "Address must be filled in");
            return;
        }
        for (int i = 0; i < address.length(); i++) {
            if (!Character.isDigit(address.charAt(i)) && !Character.isAlphabetic(address.charAt(i)) && address.charAt(i) != ' ') {

                fadeTransition(studentNotifLbl, "Address is invalid");
                return;
            }
        }

        String profession = "";
        if (studentProfessionCB.getSelectionModel().getSelectedItem() == null) {

            fadeTransition(studentNotifLbl, "Profession must be filled in");
            return;
        }
        profession = studentProfessionCB.getSelectionModel().getSelectedItem();

        if (profession.compareTo("") == 0) {

            fadeTransition(studentNotifLbl, "Profession must be filled in");
            return;
        }

        String gender = studentGenderCB.getSelectionModel().getSelectedItem();
        if (gender.compareTo("") == 0) {

            fadeTransition(studentNotifLbl, "Gender must be filled in");
            return;
        }

        LocalDate dateOfBirth = studentDateOfBirthDP.getValue();

        if (dateOfBirth == null) {
            fadeTransition(studentNotifLbl, "Date of birth must be filled in");
            return;
        }

        
        String year = (String)(studentYearSelectCB.getSelectionModel().getSelectedItem());

        String id = getProfessionID(profession);

        String SQL2 = "Insert into Student (Name,Profession_ID,National_ID,Date_Of_Birth,Address,Contact1,Contact2,Gender,class_year,guardian_name)\n"
                + "values ('"
                + name
                + "','"
                + id
                + "',"
                + nationalID
                + ",DATE '"
                + dateOfBirth
                + "','"
                + address
                + "',"
                + contact1
                + ","
                + contact2
                + ",'"
                + gender
                + "',"
                + year
                + ",'"
                + guardianName
                + "');";

        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        Statement stmt2 = con.createStatement();
        stmt2.executeUpdate(SQL2);
        
        stmt2.close();
        con.close();

        loadStudents();

        con = a.connectDB();

        String studentIDQuery = "SELECT student_ID FROM Student ORDER BY student_ID DESC LIMIT 1";
        Statement studentIDStmt = con.createStatement();
        ResultSet studentIDRS = studentIDStmt.executeQuery(studentIDQuery);

        studentIDRS.next();
        String studentID = studentIDRS.getString(1);

        studentIDStmt.close();

        String addTuitionQuery = "insert into tuition(student_ID) values (" + studentID + ");";
        Statement addTuition = con.createStatement();
        addTuition.executeUpdate(addTuitionQuery);

        con.close();

        registerStudentSubjects(id, studentID);
        
        subjectFormStage.close();
    }

    public void registerStudentSubjects(String professionID, String studentID) throws ClassNotFoundException, SQLException {

        //Establish connection to database
        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        //Get list of subjects for given profession
        String professionSubjectsQuery = "Select Subject_ID from ProfessionSubjects where Profession_ID = " + professionID + ";";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(professionSubjectsQuery);

        LinkedList<String> subjectIDs = new LinkedList<String>();
        while (rs.next()) {
            subjectIDs.add(rs.getString(1));
        }

        stmt.close();
        
        for (int i = 0; i < subjectIDs.size(); i++) {
            enrollStudent(studentID, subjectIDs.get(i));
        }
        con.close();

    }

    public void enrollStudent(String studentID, String subjectID) throws SQLException {

        String enrollStudentQuery = "insert into enroll(student_id,subject_id) values (" + studentID + "," + subjectID + ");";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(enrollStudentQuery);
        stmt.close();
    }

    public void fillSubjectCB() throws SQLException, ClassNotFoundException {
        subjectsOL = FXCollections.observableArrayList();

        //Establish connection to database
        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        //Get current list of professions and add it to studentProfessionCB
        String SQL = "Select name from Subject ORDER BY name;";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        //Add professions to studentProfessionCB
        while (rs.next()) {
            subjectsOL.add(rs.getString(1));
        }
        subjectCB.setItems(subjectsOL);

        //Close connection
        rs.close();
        stmt.close();
        con.close();
    }

    public String getSubjectID(String name) throws ClassNotFoundException, SQLException {

        //Establish connection to database
        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        String SQL = "Select subject_ID from Subject where Name = '" + name + "';";
        
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        rs.next();
        String id = rs.getString(1);

        rs.close();
        stmt.close();
        con.close();

        return id;

    }

    public String getSubjectName(String id) throws ClassNotFoundException, SQLException {

        //Establish connection to database
        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        String SQL = "Select name from Subject where subject_ID = " + id + ";";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        rs.next();
        String name = rs.getString(1);

        rs.close();
        stmt.close();
        con.close();

        return name;

    }

    public void loadStudentEditForm() throws SQLException, ClassNotFoundException {

        loadStudentProfessions();

        if (cheapCount == 0) {
            loadStudentForm();
            cheapCount++;
        }

        studentYearCB.setVisible(false);
        studentYearLbl.setVisible(false);
        studentIDTF.setVisible(false);
        studentIDLbl.setVisible(false);
        searchStudentBtn.setVisible(false);
        editStudentBtn.setVisible(true);
        admitStudentBtn.setVisible(false);
        String[] student = studentTable.getSelectionModel().getSelectedItem();
        if (student == null) {
            return;
        }

        studentProfessionCB.getSelectionModel().select(student[3]);
        studentGenderCB.getSelectionModel().select(student[10]);
        studentNameTF.setText(student[1]);
        studentGuardianNameTF.setText(student[9]);
        studentNationalIDTF.setText(student[4]);
        studentContact1TF.setText(student[7]);
        studentContact2TF.setText(student[8]);
        studentAddressTF.setText(student[6]);

        String[] dateValue = student[5].split("-");
        int year = Integer.parseInt(dateValue[0]);
        int month = Integer.parseInt(dateValue[1]);
        int day = Integer.parseInt(dateValue[2]);
        studentDateOfBirthDP.setValue(LocalDate.of(year, month, day));
        
        studentFormStage.setTitle("Edit Student Form");
        
        studentFormStage.show();
    }

    public void editStudent() throws SQLException, ClassNotFoundException {

        String[] student = studentTable.getSelectionModel().getSelectedItem();
        String professionIDBefore = getProfessionID(student[3]);

        String name = studentNameTF.getText().trim();
        if (name.compareTo("") == 0) {

            fadeTransition(studentNotifLbl, "Name must be filled in");
            return;
        }
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isLetter(name.charAt(i)) && name.charAt(i) != ' ') {

                fadeTransition(studentNotifLbl, "Name is invalid");
                return;
            }
        }

        String guardianName = studentGuardianNameTF.getText().trim();
        if (guardianName.compareTo("") == 0) {

            fadeTransition(studentNotifLbl, "Guardian Name must be filled in");
            return;
        }
        for (int i = 0; i < guardianName.length(); i++) {
            if (!Character.isLetter(guardianName.charAt(i)) && guardianName.charAt(i) != ' ') {

                fadeTransition(studentNotifLbl, "Guardian Name is invalid");
                return;
            }
        }

        String nationalID = studentNationalIDTF.getText().trim();
        if (nationalID.compareTo("") == 0) {

            fadeTransition(studentNotifLbl, "National ID must be filled in");
            return;
        }
        for (int i = 0; i < nationalID.length(); i++) {
            if (!Character.isDigit(nationalID.charAt(i))) {

                fadeTransition(studentNotifLbl, "National ID is invalid");
                return;
            }
        }

        String contact1 = studentContact1TF.getText().trim();
        if (contact1.compareTo("") == 0) {

            fadeTransition(studentNotifLbl, "Contact 1 must be filled in");
            return;
        }
        for (int i = 0; i < contact1.length(); i++) {
            if (!Character.isDigit(contact1.charAt(i))) {

                fadeTransition(studentNotifLbl, "Contact 1 is invalid");
                return;
            }
        }

        String contact2 = studentContact2TF.getText().trim();
        if (contact2.compareTo("") == 0) {
            contact2 = "0";
        }
        for (int i = 0; i < contact2.length(); i++) {
            if (!Character.isDigit(contact2.charAt(i))) {

                fadeTransition(studentNotifLbl, "Contact 2 is invalid");
                return;
            }
        }

        String address = studentAddressTF.getText().trim();
        if (address.compareTo("") == 0) {

            fadeTransition(studentNotifLbl, "Address must be filled in");
            return;
        }
        for (int i = 0; i < address.length(); i++) {
            if (!Character.isDigit(address.charAt(i)) && !Character.isAlphabetic(address.charAt(i)) && address.charAt(i) != ' ') {

                fadeTransition(studentNotifLbl, "Address is invalid");
                return;
            }
        }

        String profession = studentProfessionCB.getSelectionModel().getSelectedItem();
        if (profession == null) {

            fadeTransition(studentNotifLbl, "Profession must be filled in");
            return;
        }
        String professionID = getProfessionID(profession);

        if (profession.compareTo("") == 0) {
            studentNotifLbl.setText("Profession must be filled in");
            return;
        }

        String gender = studentGenderCB.getSelectionModel().getSelectedItem();
        if (gender.compareTo("") == 0) {

            fadeTransition(studentNotifLbl, "Gender must be filled in");
            return;
        }

        LocalDate dateOfBirth = studentDateOfBirthDP.getValue();

        String SQL = "update student set name = '"
                + name
                + "',date_of_birth = '"
                + dateOfBirth
                + "', national_id = "
                + nationalID
                + ", address = '"
                + address
                + "', contact1 = "
                + contact1
                + ", contact2 = "
                + contact2
                + ", gender = '"
                + gender
                + "', guardian_name = '"
                + guardianName
                + "', profession_ID = "
                + professionID
                + " where student_id = "
                + student[0]
                + ";";

        //Establish connection to database
        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        Statement stmt = con.createStatement();
        stmt.executeUpdate(SQL);
        //Close connection
        stmt.close();

        loadStudents();

        studentNotifLbl.setText("Student edited successfully!");

        if (professionID.compareTo(professionIDBefore) != 0) {

            con = a.connectDB();

            String deleteSubjectsQuery = "delete from enroll where student_ID = " + student[0] + ";";
            Statement deleteSubjectStmt = con.createStatement();
            deleteSubjectStmt.executeUpdate(deleteSubjectsQuery);

            registerStudentSubjects(professionID, student[0]);
        }

        con.close();
    }

    public void removeStudent() throws ClassNotFoundException, SQLException {
        String[] student = studentTable.getSelectionModel().getSelectedItem();
        if (student == null) {
            return;
        }

        String SQL = "DELETE FROM student WHERE student_id = " + student[0] + ";";

        //Establish connection to database
        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        Statement stmt = con.createStatement();
        try {
            stmt.executeUpdate(SQL);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        //Close connection
        stmt.close();
        con.close();

        loadStudents();

    }

    public void loadQuery(String query, String count, TableView<String[]> table, int columnWidth) throws ClassNotFoundException, SQLException {
        //Establish connection to database
        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        //Request query and load result into ResultSet
        String SQL = query;
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        //Getting number of rows and columns
        ResultSetMetaData rsmd = rs.getMetaData();

        Statement stmtCount = con.createStatement();
        ResultSet rsCount = stmtCount.executeQuery(count);
        rsCount.next();
        int columnsNumber = rsmd.getColumnCount();
        int rowsNumber = Integer.parseInt(rsCount.getString(1)) + 1;

        String[][] tableArray = new String[rowsNumber][columnsNumber];

        //Load titles into array
        for (int j = 0; j < columnsNumber; j++) {
            tableArray[0][j] = rsmd.getColumnLabel(j + 1).replaceAll("_", " ");
        }

        //Load data into array
        for (int i = 1; rs.next(); i++) {
            for (int j = 0; j < columnsNumber; j++) {
                tableArray[i][j] = rs.getString(j + 1);
            }
        }

        //Turn tableArray into ObservableList
        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(tableArray));

        //Remove column names from data
        data.remove(0);

        table.getColumns().clear();
        for (int i = 0; i < tableArray[0].length; i++) {
            TableColumn tc = new TableColumn(tableArray[0][i]);

            final int colNo = i;
            tc.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });
            tc.setPrefWidth(columnWidth);
            table.getColumns().add(tc);
        }

        table.setItems(data);

        //Close connection
        rs.close();
        rsCount.close();
        stmt.close();
        con.close();
    }

    public void loadProfessionTable() throws ClassNotFoundException, SQLException {
        String SQL = "Select Profession_Name from Profession;";
        String SQLCount = "Select count(*) from Profession;";
        loadQuery(SQL, SQLCount, professionTable, 368);
    }

    public void loadProfessionForm(){
        AnchorPane ap = new AnchorPane();

            ap.setPrefHeight(269.0);
            ap.setPrefWidth(649.0);

            requestLbl.setLayoutX(173.0);
            requestLbl.setLayoutY(81.0);
            requestLbl.setText("Write the name of the profession to be added");

            professionNameTF.setLayoutX(173.0);
            professionNameTF.setLayoutY(119.0);
            professionNameTF.setPrefHeight(31.0);
            professionNameTF.setPrefWidth(304.0);

            addProfessionBtn.setLayoutX(295.0);
            addProfessionBtn.setLayoutY(172.0);
            addProfessionBtn.setMnemonicParsing(false);
            addProfessionBtn.setText("Add");
            addProfessionBtn.setOnAction(e -> {
                try {
                    addProfession();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            });

            ap.getChildren().add(requestLbl);
            ap.getChildren().add(professionNameTF);
            ap.getChildren().add(addProfessionBtn);

            Scene scene = new Scene(ap);
            professionFormStage.setScene(scene);
            professionFormStage.setResizable(false);
            professionFormStage.initModality(Modality.APPLICATION_MODAL);
            professionFormStage.getIcons().add(new Image(LWF.class.getResourceAsStream("logo.png")));
    }
    
    boolean isProfessionFormLoaded = false;
    public void loadProfessionAddForm() {

        if (!isProfessionFormLoaded) {
            loadProfessionForm();
            isProfessionFormLoaded = true;
        }

        requestLbl.setText("Write the name of the profession to be added");
        addProfessionBtn.setOnAction(e -> {
            try {
                addProfession();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });

        addProfessionBtn.setText("Add");
        requestLbl.setText("Write the name of the profession to be added");
        
        
        professionFormStage.setTitle("Profession Add Form");
        professionFormStage.show();
    }

    public void loadProfessionEditForm() {

        if (professionTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        if (!isProfessionFormLoaded) {
            loadProfessionForm();
            isProfessionFormLoaded = true;
        }

        requestLbl.setText("Set new name for the selected profession");
        addProfessionBtn.setOnAction(e -> {
            try {
                editProfession();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });
        professionNameTF.setText(professionTable.getSelectionModel().getSelectedItem()[0]);
        addProfessionBtn.setText("Edit");
        professionFormStage.setTitle("Profession Edit Form");
        professionFormStage.show();

    }

    public void editProfession() throws ClassNotFoundException, SQLException {

        String professionName = professionNameTF.getText().trim().replaceAll("\\s{2,}", " ");

        if (professionName.compareTo("") == 0) {
            return;
        }

        //Establish connection to database
        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();
        Statement stmt = con.createStatement();
        String query = "update profession set profession_name = '" + professionName + "' where profession_name = '" + professionTable.getSelectionModel().getSelectedItem()[0] + "';";
        stmt.executeUpdate(query);
        stmt.close();

        loadProfessionTable();

        professionFormStage.close();
    }

    public void addProfession() throws ClassNotFoundException, SQLException {
        String professionName = professionNameTF.getText().trim().replaceAll("\\s{2,}", " ");

        if (professionName.compareTo("") == 0) {
            return;
        }

        //Establish connection to database
        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();
        Statement stmt = con.createStatement();
        String query = "insert into profession (profession_name) values ('" + professionName + "');";
        stmt.executeUpdate(query);
        stmt.close();

        loadProfessionTable();

        professionFormStage.close();

    }

    public void removeProfession() throws ClassNotFoundException, SQLException {
        if (professionTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        String professionName = professionTable.getSelectionModel().getSelectedItem()[0];

        String query = "delete from profession where profession_name = '" + professionName + "';";

        //Establish connection to database
        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();
        Statement stmt = con.createStatement();

        try {
            stmt.executeUpdate(query);
        } catch (Exception ex) {
            fadeTransition(professionNotifLbl, "Removal failed: Profession has students registered");
        }
        stmt.close();
        con.close();

        loadProfessionTable();

    }

    public void loadStudentQuery(String query, String count, TableView<String[]> table, int columnWidth) throws ClassNotFoundException, SQLException {
        //Establish connection to database
        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        //Request query and load result into ResultSet
        String SQL = query;
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        //Getting number of rows and columns
        ResultSetMetaData rsmd = rs.getMetaData();

        Statement stmtCount = con.createStatement();
        ResultSet rsCount = stmtCount.executeQuery(count);
        rsCount.next();
        int columnsNumber = rsmd.getColumnCount();
        int rowsNumber = Integer.parseInt(rsCount.getString(1)) + 1;

        String[][] tableArray = new String[rowsNumber][columnsNumber];

        //Load titles into array
        for (int j = 0; j < columnsNumber; j++) {
            tableArray[0][j] = rsmd.getColumnLabel(j + 1).replaceAll("_", " ");
        }

        //Load data into array
        for (int i = 1; rs.next(); i++) {
            for (int j = 0; j < columnsNumber; j++) {
                tableArray[i][j] = rs.getString(j + 1);
            }
        }

        tableArray[0][3] = "Profession Name";
        for (int i = 1; i < tableArray.length; i++) {
            tableArray[i][3] = getProfessionName(tableArray[i][3]);
        }

        //Turn tableArray into ObservableList
        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(tableArray));

        //Remove column names from data
        data.remove(0);

        table.getColumns().clear();
        for (int i = 0; i < tableArray[0].length; i++) {
            TableColumn tc = new TableColumn(tableArray[0][i]);

            final int colNo = i;
            tc.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });
            tc.setPrefWidth(columnWidth);
            table.getColumns().add(tc);
        }

        table.setItems(data);

        //Close connection
        rs.close();
        rsCount.close();
        stmt.close();
        con.close();

    }

    public void fadeTransition(Label lbl, String text) {

        lbl.setOpacity(1);
        lbl.setText(text);
        FadeTransition ft = new FadeTransition(Duration.millis(500), lbl);
        ft.setDelay(Duration.millis(2500));
        ft.setFromValue(1);
        ft.setToValue(0);

        ft.play();
    }

    public void addSubjectToProfession() throws ClassNotFoundException, SQLException {
        if (professionTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        if (subjectCB.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        String professionName = professionTable.getSelectionModel().getSelectedItem()[0];
        String subjectName = subjectCB.getSelectionModel().getSelectedItem();
        String professionID = getProfessionID(professionName);
        String subjectID = getSubjectID(subjectName);

        String SQL = "insert into professionSubjects(Profession_ID,Subject_ID) values(" + professionID + "," + subjectID + ");";

        //Establish connection to database
        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        Statement stmt = con.createStatement();
        try {
            stmt.executeUpdate(SQL);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        //Close connection
        stmt.close();
        con.close();

        String queryCount = "select count(*) from ProfessionSubjects ps, Subject s where s.subject_ID = ps.subject_ID AND ps.profession_ID = '" + professionID + "';";
        String query = "select s.Subject_ID,s.Name,s.Semester,s.Total_Grade from ProfessionSubjects ps, Subject s where s.subject_ID = ps.subject_ID AND ps.profession_ID = '" + professionID + "';";
        try {
            loadQuery(query, queryCount, professionSubjectsTable, 140);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void removeSubjectFromProfession() throws ClassNotFoundException, SQLException {

        if (professionTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        if (professionSubjectsTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        String professionName = professionTable.getSelectionModel().getSelectedItem()[0];
        String subjectName = professionSubjectsTable.getSelectionModel().getSelectedItem()[1];
        String professionID = getProfessionID(professionName);
        String subjectID = getSubjectID(subjectName);

        String SQL = "delete from professionSubjects where subject_id = " + subjectID + " AND profession_id = " + professionID + ";";

        //Establish connection to database
        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        Statement stmt = con.createStatement();
        try {
            stmt.executeUpdate(SQL);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        //Close connection
        stmt.close();
        con.close();

        String queryCount = "select count(*) from ProfessionSubjects ps, Subject s where s.subject_ID = ps.subject_ID AND ps.profession_ID = '" + professionID + "';";
        String query = "select s.Subject_ID,s.Name,s.Semester,s.Total_Grade from ProfessionSubjects ps, Subject s where s.subject_ID = ps.subject_ID AND ps.profession_ID = '" + professionID + "';";
        try {
            loadQuery(query, queryCount, professionSubjectsTable, 140);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    boolean isTuitionFormLoaded = false;
    public void loadTuitionForm() throws ClassNotFoundException, SQLException {

        if (studentTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        if (!isTuitionFormLoaded) {

            AnchorPane ap = new AnchorPane();

            ap.setPrefHeight(297.0);
            ap.setPrefWidth(411.0);

            payAmountTF.setLayoutX(107.0);
            payAmountTF.setLayoutY(177.0);
            payAmountTF.setPrefHeight(31.0);
            payAmountTF.setPrefWidth(121.0);

            amountPaidLbl.setLayoutX(107.0);
            amountPaidLbl.setLayoutY(137.0);
            amountPaidLbl.setText("Amount Paid:");

            amountRequiredLbl.setLayoutX(107.0);
            amountRequiredLbl.setLayoutY(90.0);
            amountRequiredLbl.setText("Amount Required:");

            requiredLbl.setLayoutX(242.0);
            requiredLbl.setLayoutY(90.0);
            requiredLbl.setText("4000 NIS");

            paidLbl.setLayoutX(242.0);
            paidLbl.setLayoutY(137.0);
            paidLbl.setText("0 NIS");

            payBtn.setLayoutX(240.0);
            payBtn.setLayoutY(177.0);
            payBtn.setMnemonicParsing(false);
            payBtn.setPrefHeight(31.0);
            payBtn.setPrefWidth(60.0);
            payBtn.setText("Pay");
            payBtn.setOnAction(e -> {
                pay();
            });

            tuitionNotifLbl.setLayoutX(107.0);
            tuitionNotifLbl.setLayoutY(215.0);

            ap.getChildren().add(payAmountTF);
            ap.getChildren().add(amountPaidLbl);
            ap.getChildren().add(amountRequiredLbl);
            ap.getChildren().add(requiredLbl);
            ap.getChildren().add(paidLbl);
            ap.getChildren().add(payBtn);
            ap.getChildren().add(tuitionNotifLbl);

            Scene scene = new Scene(ap);

            tuitionFormStage.setScene(scene);
            tuitionFormStage.setResizable(false);
            tuitionFormStage.initModality(Modality.APPLICATION_MODAL);
            
            tuitionFormStage.setTitle("Tuition Form");
            tuitionFormStage.getIcons().add(new Image(LWF.class.getResourceAsStream("logo.png")));
            
            isTuitionFormLoaded = true;
        }

        String studentID = studentTable.getSelectionModel().getSelectedItem()[0];

        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        String tuitionQuery = "Select * from tuition where Student_ID = " + studentID + ";";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(tuitionQuery);

        rs.next();
        String amountRequired = rs.getString(2);
        String amountPaid = rs.getString(3);

        rs.close();
        stmt.close();
        con.close();

        paidLbl.setText(amountPaid + " NIS");
        requiredLbl.setText(amountRequired + " NIS");

        tuitionFormStage.show();
    }

    public void pay() {

        try {

            String studentID = studentTable.getSelectionModel().getSelectedItem()[0];

            int requiredAmount = Integer.parseInt(requiredLbl.getText().split(" ")[0]);
            int paidAmount = Integer.parseInt(paidLbl.getText().split(" ")[0]);
            int amountToPay = Integer.parseInt(payAmountTF.getText().trim());

            if (amountToPay > requiredAmount) {
                amountToPay = requiredAmount;
                paidAmount = amountToPay + paidAmount;
                requiredAmount = requiredAmount - amountToPay;
            } else {
                paidAmount = amountToPay + paidAmount;
                requiredAmount = requiredAmount - amountToPay;
            }

            Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
            con = a.connectDB();

            String tuitionQuery = "update tuition set amount_paid = " + paidAmount + ", amount_required = " + requiredAmount + " where student_id = " + studentID + ";";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(tuitionQuery);

            stmt.close();
            con.close();

            requiredLbl.setText(Integer.toString(requiredAmount) + " NIS");
            paidLbl.setText(Integer.toString(paidAmount) + " NIS");

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            fadeTransition(tuitionNotifLbl, "Invalid amount");
        }

    }
    
    public void loadSubjectForm() {
        AnchorPane ap = new AnchorPane();

            ap.setPrefHeight(400.0);
            ap.setPrefWidth(514.0);

            subjectNameLbl.setLayoutX(93.0);
            subjectNameLbl.setLayoutY(80.0);
            subjectNameLbl.setText("Subject Name:");

            semesterLbl.setLayoutX(93.0);
            semesterLbl.setLayoutY(160.0);
            semesterLbl.setText("Semester:");

            totalGradeLbl.setLayoutX(93.0);
            totalGradeLbl.setLayoutY(240.0);
            totalGradeLbl.setText("Total Grade:");

            subjectNameTF.setLayoutX(207.0);
            subjectNameTF.setLayoutY(75.0);
            
            totalGradeTF.setLayoutX(207.0);
            totalGradeTF.setLayoutY(235.0);

            addSubjectBtn.setLayoutX(207.0);
            addSubjectBtn.setLayoutY(305.0);
            addSubjectBtn.setMnemonicParsing(false);
            addSubjectBtn.setText("Add Subject");

            semesterCB.setLayoutX(207.0);
            semesterCB.setLayoutY(155.0);
            semesterCB.setPrefHeight(31.0);
            semesterCB.setPrefWidth(65.0);
            semesterCB.setItems(semestersOL);

            subjectNotifLbl.setLayoutX(207.0);
            subjectNotifLbl.setLayoutY(345.0);

            ap.getChildren().add(subjectNameLbl);
            ap.getChildren().add(semesterLbl);
            ap.getChildren().add(totalGradeLbl);
            ap.getChildren().add(subjectNameTF);
            ap.getChildren().add(totalGradeTF);
            ap.getChildren().add(addSubjectBtn);
            ap.getChildren().add(semesterCB);
            ap.getChildren().add(subjectNotifLbl);

            Scene scene = new Scene(ap);

            subjectFormStage.setResizable(false);
            subjectFormStage.initModality(Modality.APPLICATION_MODAL);
            subjectFormStage.setScene(scene);
            
            subjectFormStage.getIcons().add(new Image(LWF.class.getResourceAsStream("logo.png")));
    }
    
    boolean isSubjectFormLoaded = false;
    public void loadSubjectAddForm() {

        if (!isSubjectFormLoaded) {
            loadSubjectForm();
            isSubjectFormLoaded = true;
        }

        addSubjectBtn.setOnAction(e -> {
            try {
                addSubject();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        });

        addSubjectBtn.setText("Add Subject");

        subjectFormStage.setTitle("Subject Add Form");
        
        subjectFormStage.show();
    }

    public void loadSubjectEditForm() {

        if (!isSubjectFormLoaded) {
            loadSubjectForm();
            isSubjectFormLoaded = true;
        }

        addSubjectBtn.setOnAction(e -> {
            try {
                editSubject();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });

        addSubjectBtn.setText("Edit Subject");

        String[] subject = subjectTable.getSelectionModel().getSelectedItem();

        subjectNameTF.setText(subject[0]);
        semesterCB.getSelectionModel().select(subject[1]);
        totalGradeTF.setText(subject[2]);

        subjectFormStage.setTitle("Subject Edit Form");
        
        subjectFormStage.show();
    }

    public void addSubject() throws ClassNotFoundException, SQLException {

        String subjectName = subjectNameTF.getText().trim();
        if (subjectName.compareTo("") == 0) {
            fadeTransition(subjectNotifLbl, "Subject Name must be entered");
            return;
        }

        for (int i = 0; i < subjectName.length(); i++) {
            if (!Character.isLetter(subjectName.charAt(i)) && subjectName.charAt(i) != ' ') {
                fadeTransition(subjectNotifLbl, "Invalid Subject Name");
                return;
            }
        }

        if (semesterCB.getSelectionModel().getSelectedItem() == null) {
            fadeTransition(subjectNotifLbl, "Semester must be entered");
            return;
        }

        String semester = (String) semesterCB.getSelectionModel().getSelectedItem();

        int totalGrade = 0;
        try {
            totalGrade = Integer.parseInt(totalGradeTF.getText().trim());
        } catch (Exception ex) {
            fadeTransition(subjectNotifLbl, "Invalid Total Grade");
            return;
        }

        String totalGradeSt = Integer.toString(totalGrade);

        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        String addSubjectQuery = "insert into subject(name,semester,total_grade) values('" + subjectName + "'," + semester + "," + totalGradeSt + ");";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(addSubjectQuery);

        stmt.close();
        con.close();

        loadSubjects();

        subjectFormStage.close();
    }

    public void editSubject() throws ClassNotFoundException, SQLException {

        String subjectName = subjectNameTF.getText().trim();
        if (subjectName.compareTo("") == 0) {
            fadeTransition(subjectNotifLbl, "Subject Name must be entered");
            return;
        }

        for (int i = 0; i < subjectName.length(); i++) {
            if (!Character.isLetter(subjectName.charAt(i))) {
                fadeTransition(subjectNotifLbl, "Invalid Subject Name");
                return;
            }
        }

        if (semesterCB.getSelectionModel().getSelectedItem() == null) {
            fadeTransition(subjectNotifLbl, "Semester must be entered");
            return;
        }

        String semester = (String) semesterCB.getSelectionModel().getSelectedItem();

        int totalGrade = 0;
        try {
            totalGrade = Integer.parseInt(totalGradeTF.getText().trim());
        } catch (Exception ex) {
            fadeTransition(subjectNotifLbl, "Invalid Total Grade");
            return;
        }

        String totalGradeSt = Integer.toString(totalGrade);

        String[] subject = subjectTable.getSelectionModel().getSelectedItem();

        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        String addSubjectQuery = "update subject set semester = " + semester + ", name = '" + subjectName + "', total_grade = " + totalGrade + " where subject_id = " + subject[0] + ";";
        
        Statement stmt = con.createStatement();
        stmt.executeUpdate(addSubjectQuery);

        stmt.close();
        con.close();

        loadSubjects();

        subjectFormStage.close();

    }

    public void removeSubject() throws ClassNotFoundException, SQLException {

        if (subjectTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        String[] subject = subjectTable.getSelectionModel().getSelectedItem();

        String subjectID = getSubjectID(subject[0]);
        
        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();
        
        String removeSubjectQuery = "delete from subject where subject_id = " + subjectID + ";";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(removeSubjectQuery);

        stmt.close();
        con.close();

        loadSubjects();
    }

    boolean isAddTeacherToSubjectFormLoaded = false;

    public void loadAddTeacherToSubjectForm() throws ClassNotFoundException, SQLException {

        if (subjectTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        if (!isAddTeacherToSubjectFormLoaded) {

            AnchorPane ap = new AnchorPane();

            ap.setPrefHeight(510.0);
            ap.setPrefWidth(376.0);

            addTeacherToSubjectTable.setLayoutX(48.0);
            addTeacherToSubjectTable.setLayoutY(42.0);

            addTeacherToSubjectTable.setPrefHeight(401.0);
            addTeacherToSubjectTable.setPrefWidth(280.0);

            addTeacherToSubjectBtn.setLayoutX(136.0);
            addTeacherToSubjectBtn.setLayoutY(465.0);
            addTeacherToSubjectBtn.setMnemonicParsing(false);
            addTeacherToSubjectBtn.setText("Add Teacher");
            addTeacherToSubjectBtn.setOnAction(e -> {
                try {
                    addTeacherToSubject();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

            });

            ap.getChildren().add(addTeacherToSubjectTable);
            ap.getChildren().add(addTeacherToSubjectBtn);

            Scene scene = new Scene(ap);

            addTeacherToSubjectStage.setScene(scene);
            addTeacherToSubjectStage.initModality(Modality.APPLICATION_MODAL);
            addTeacherToSubjectStage.setResizable(false);

            isAddTeacherToSubjectFormLoaded = true;
        }

        String query = "select * from employee where employee_ID in (select teacher_id from teacher);";
        String queryCount = "select count(*) from employee where employee_ID in (select teacher_id from teacher);";

        loadQuery(query, queryCount, addTeacherToSubjectTable, 140);

        addTeacherToSubjectStage.show();
    }

    public void addTeacherToSubject() throws ClassNotFoundException, SQLException {

        if (addTeacherToSubjectTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        String subjectName = subjectTable.getSelectionModel().getSelectedItem()[0];

        String subjectID = getSubjectID(subjectName);
        String teacherID = addTeacherToSubjectTable.getSelectionModel().getSelectedItem()[0];

        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        String query = "insert into teaching(subject_id,teacher_id) values(" + subjectID + "," + teacherID + ");";

        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);

        stmt.close();
        con.close();

        String id = getSubjectID(subjectTable.getSelectionModel().getSelectedItem()[0]);
        String queryCount = "select count(*) from teaching te,teacher t where te.teacher_ID = t.teacher_ID AND te.subject_id = " + id + ";";
        String queryReload = "select Employee_ID, Name from teaching te,employee t where te.teacher_ID = t.employee_ID AND te.subject_id = " + id + ";";

        loadQuery(queryReload, queryCount, subjectInfoTable, 140);

        addTeacherToSubjectStage.close();
    }

    public void loadEmployees() throws ClassNotFoundException, SQLException {
        String query = "select * from Employee;";
        String queryCount = "select count(*) from Employee";

        loadQuery(query, queryCount, employeeTable, 128);
    }

    boolean isEmployeeFormLoaded = false;

    public void clearEmployeeForm() {
        employeeIDTF.setText("");
        employeeNameTF.setText("");
        employeeNationalIDTF.setText("");
        employeeAddressTF.setText("");
        employeeContact1TF.setText("");
        employeeContact2TF.setText("");
        employeeGenderCB.getSelectionModel().clearSelection();
        employeeDateOfBirthDP.setValue(null);
    }

    public void loadEmployeeAddForm() throws ClassNotFoundException, SQLException {

        if (!isEmployeeFormLoaded) {
            loadEmployeeForm();
            isEmployeeFormLoaded = true;
        }
        
        employeeFormStage.setTitle("Employee Add Form");
        employeeIDTF.setVisible(false);
        employeeIDLbl.setVisible(false);
        searchEmployeeBtn.setVisible(false);
        admitEmployeeBtn.setVisible(true);
        editEmployeeBtn.setVisible(false);

        clearEmployeeForm();

        employeeFormStage.show();

    }

    public void loadEmployeeEditForm() throws ClassNotFoundException, SQLException {

        if (!isEmployeeFormLoaded) {

            loadEmployeeForm();
            isEmployeeFormLoaded = true;
        }

        employeeIDTF.setVisible(false);
        employeeIDLbl.setVisible(false);
        searchEmployeeBtn.setVisible(false);
        editEmployeeBtn.setVisible(true);
        admitEmployeeBtn.setVisible(false);
        String[] employee = employeeTable.getSelectionModel().getSelectedItem();
        if (employee == null) {
            return;
        }

        employeeGenderCB.getSelectionModel().select(employee[7]);
        employeeNameTF.setText(employee[1]);
        employeeNationalIDTF.setText(employee[2]);
        employeeContact1TF.setText(employee[5]);
        employeeContact2TF.setText(employee[6]);
        employeeAddressTF.setText(employee[4]);

        String[] dateValue = employee[3].split("-");
        int year = Integer.parseInt(dateValue[0]);
        int month = Integer.parseInt(dateValue[1]);
        int day = Integer.parseInt(dateValue[2]);
        employeeDateOfBirthDP.setValue(LocalDate.of(year, month, day));
        
        
        employeeFormStage.setTitle("Employee Edit Form");
        employeeFormStage.show();
    }

    public void admitEmployee() throws ClassNotFoundException, SQLException {

        String name = employeeNameTF.getText().trim();
        if (name.compareTo("") == 0) {
            fadeTransition(employeeNotifLbl, "Name must be filled in");
            return;
        }
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isLetter(name.charAt(i)) && name.charAt(i) != ' ') {

                fadeTransition(employeeNotifLbl, "Name is invalid");
                return;
            }
        }

        String nationalID = employeeNationalIDTF.getText().trim();
        if (nationalID.compareTo("") == 0) {

            fadeTransition(employeeNotifLbl, "National ID must be filled in");
            return;
        }
        for (int i = 0; i < nationalID.length(); i++) {
            if (!Character.isDigit(nationalID.charAt(i))) {

                fadeTransition(employeeNotifLbl, "National ID is invalid");
                return;
            }
        }

        String contact1 = employeeContact1TF.getText().trim();
        if (contact1.compareTo("") == 0) {

            fadeTransition(employeeNotifLbl, "Contact 1 must be filled in");
            return;
        }
        for (int i = 0; i < contact1.length(); i++) {
            if (!Character.isDigit(contact1.charAt(i))) {

                fadeTransition(employeeNotifLbl, "Contact 1 is invalid");
                return;
            }
        }

        String contact2 = employeeContact2TF.getText().trim();
        if (contact2.compareTo("") == 0) {
            contact2 = "0";
        }
        for (int i = 0; i < contact2.length(); i++) {
            if (!Character.isDigit(contact2.charAt(i))) {

                fadeTransition(employeeNotifLbl, "Contact 2 is invalid");
                return;
            }
        }

        String address = employeeAddressTF.getText().trim();
        if (address.compareTo("") == 0) {

            fadeTransition(employeeNotifLbl, "Adress must be filled in");
            return;
        }
        for (int i = 0; i < address.length(); i++) {
            if (!Character.isDigit(address.charAt(i)) && !Character.isAlphabetic(address.charAt(i)) && address.charAt(i) != ' ') {

                fadeTransition(employeeNotifLbl, "Address is invalid");
                return;
            }
        }

        String gender = "";
        if (employeeGenderCB.getSelectionModel().getSelectedItem() == null) {

            fadeTransition(employeeNotifLbl, "Gender must be filled in");
            return;
        }
        gender = employeeGenderCB.getSelectionModel().getSelectedItem();

        if (employeeDateOfBirthDP.getValue() == null) {
            fadeTransition(employeeNotifLbl, "Date of birth must be filled in");
            return;
        }

        LocalDate dateOfBirth = employeeDateOfBirthDP.getValue();

        String SQL2 = "Insert into Employee (Name,National_ID,Date_Of_Birth,Address,Contact1,Contact2,Gender)\n"
                + "values ('"
                + name
                + "',"
                + nationalID
                + ",DATE '"
                + dateOfBirth
                + "','"
                + address
                + "',"
                + contact1
                + ","
                + contact2
                + ",'"
                + gender
                + "');";

        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        Statement stmt = con.createStatement();
        stmt.executeUpdate(SQL2);

        stmt.close();
        con.close();

        loadEmployees();

        employeeFormStage.close();
    }

    public void editEmployee() throws ClassNotFoundException, SQLException {

        String name = employeeNameTF.getText().trim();
        if (name.compareTo("") == 0) {

            fadeTransition(employeeNotifLbl, "Name must be filled in");
            return;
        }
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isLetter(name.charAt(i)) && name.charAt(i) != ' ') {

                fadeTransition(employeeNotifLbl, "Name is invalid");
                return;
            }
        }

        String nationalID = employeeNationalIDTF.getText().trim();
        if (nationalID.compareTo("") == 0) {

            fadeTransition(employeeNotifLbl, "National ID must be filled in");
            return;
        }
        for (int i = 0; i < nationalID.length(); i++) {
            if (!Character.isDigit(nationalID.charAt(i))) {

                fadeTransition(employeeNotifLbl, "National ID is invalid");
                return;
            }
        }

        String contact1 = employeeContact1TF.getText().trim();
        if (contact1.compareTo("") == 0) {

            fadeTransition(employeeNotifLbl, "Contact 1 must be filled in");
            return;
        }
        for (int i = 0; i < contact1.length(); i++) {
            if (!Character.isDigit(contact1.charAt(i))) {

                fadeTransition(employeeNotifLbl, "Contact 1 is invalid");
                return;
            }
        }

        String contact2 = employeeContact2TF.getText().trim();
        if (contact2.compareTo("") == 0) {
            contact2 = "0";
        }
        for (int i = 0; i < contact2.length(); i++) {
            if (!Character.isDigit(contact2.charAt(i))) {

                fadeTransition(employeeNotifLbl, "Contact 2 is invalid");
                return;
            }
        }

        String address = employeeAddressTF.getText().trim();
        if (address.compareTo("") == 0) {

            fadeTransition(employeeNotifLbl, "Address must be filled in");
            return;
        }
        for (int i = 0; i < address.length(); i++) {
            if (!Character.isDigit(address.charAt(i)) && !Character.isAlphabetic(address.charAt(i)) && address.charAt(i) != ' ') {

                fadeTransition(employeeNotifLbl, "Address is invalid");
                return;
            }
        }

        String gender = employeeGenderCB.getSelectionModel().getSelectedItem();
        if (gender.compareTo("") == 0) {

            fadeTransition(employeeNotifLbl, "Gender must be filled in");
            return;
        }

        LocalDate dateOfBirth = employeeDateOfBirthDP.getValue();

        String[] employee = employeeTable.getSelectionModel().getSelectedItem();

        String SQL2 = "update employee set name = '"
                + name
                + "', national_ID = "
                + nationalID
                + ", address = '"
                + address
                + "', contact1 = "
                + contact1
                + ", contact2 = "
                + contact2
                + ", gender = '"
                + gender
                + "', date_Of_Birth = '"
                + dateOfBirth
                + "' where employee_ID = "
                + employee[0]
                + ";";

        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        Statement stmt = con.createStatement();
        stmt.executeUpdate(SQL2);

        stmt.close();
        con.close();

        loadEmployees();
    }

    public void removeEmployee() throws ClassNotFoundException, SQLException {

        if (employeeTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        String type = ((RadioButton) employeeTypeTG.getSelectedToggle()).getText();
        String SQL = "";

        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        if (type.compareTo("All") == 0) {

            SQL = "delete from employee where employee_id = " + employeeTable.getSelectionModel().getSelectedItem()[0] + ";";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(SQL);

            stmt.close();
            con.close();

            loadEmployees();

        } else if (type.compareTo("Teachers") == 0) {

            SQL = "delete from teacher where teacher_id = " + employeeTable.getSelectionModel().getSelectedItem()[0] + ";";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(SQL);

            stmt.close();
            con.close();

            loadTeachers();

        } else if (type.compareTo("Managers") == 0) {

            SQL = "delete from manager where manager_id = " + employeeTable.getSelectionModel().getSelectedItem()[0] + ";";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(SQL);

            stmt.close();
            con.close();

            loadManagers();

        } else if (type.compareTo("Secretaries") == 0) {

            SQL = "delete from secretary where secretary_id = " + employeeTable.getSelectionModel().getSelectedItem()[0] + ";";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(SQL);

            stmt.close();
            con.close();

            loadSecretaries();

        }

    }

    public void loadEmployeeForm() {
        AnchorPane.setTopAnchor(employeeNameTF, 44.0);
        AnchorPane.setLeftAnchor(employeeNameTF, 161.0);

        AnchorPane.setTopAnchor(employeeNameLbl, 49.0);
        AnchorPane.setLeftAnchor(employeeNameLbl, 40.0);

        AnchorPane.setTopAnchor(employeeNationalIDTF, 226.0);
        AnchorPane.setLeftAnchor(employeeNationalIDTF, 161.0);

        AnchorPane.setTopAnchor(employeeNationalIDLbl, 231.0);
        AnchorPane.setLeftAnchor(employeeNationalIDLbl, 40.0);

        AnchorPane.setTopAnchor(employeeAddressTF, 319.0);
        AnchorPane.setLeftAnchor(employeeAddressTF, 161.0);

        AnchorPane.setTopAnchor(employeeAddressLbl, 324.0);
        AnchorPane.setLeftAnchor(employeeAddressLbl, 40.0);

        AnchorPane.setTopAnchor(employeeContact1TF, 381.0);
        AnchorPane.setLeftAnchor(employeeContact1TF, 161.0);

        AnchorPane.setTopAnchor(employeeContact1Lbl, 386.0);
        AnchorPane.setLeftAnchor(employeeContact1Lbl, 40.0);

        AnchorPane.setTopAnchor(employeeContact2TF, 412.0);
        AnchorPane.setLeftAnchor(employeeContact2TF, 161.0);

        AnchorPane.setTopAnchor(employeeContact2Lbl, 417.0);
        AnchorPane.setLeftAnchor(employeeContact2Lbl, 40.0);

        AnchorPane.setTopAnchor(employeeDateOfBirthDP, 226.0);
        AnchorPane.setLeftAnchor(employeeDateOfBirthDP, 481.0);
        employeeDateOfBirthDP.setPrefHeight(31);
        employeeDateOfBirthDP.setPrefWidth(234);
        employeeDateOfBirthDP.setEditable(false);

        AnchorPane.setTopAnchor(employeeDateOfBirthLbl, 200.0);
        AnchorPane.setLeftAnchor(employeeDateOfBirthLbl, 481.0);

        AnchorPane.setTopAnchor(employeeGenderCB, 133.0);
        AnchorPane.setLeftAnchor(employeeGenderCB, 565.0);

        AnchorPane.setTopAnchor(employeeGenderLbl, 138.0);
        AnchorPane.setLeftAnchor(employeeGenderLbl, 483.0);

        AnchorPane.setTopAnchor(employeeIDTF, 44.0);
        AnchorPane.setLeftAnchor(employeeIDTF, 530.0);

        AnchorPane.setTopAnchor(employeeIDLbl, 49.0);
        AnchorPane.setLeftAnchor(employeeIDLbl, 483.0);

        AnchorPane.setTopAnchor(employeeNotifLbl, 443.0);
        AnchorPane.setLeftAnchor(employeeNotifLbl, 487.0);

        AnchorPane.setTopAnchor(admitEmployeeBtn, 381.0);
        AnchorPane.setLeftAnchor(admitEmployeeBtn, 487.0);
        admitEmployeeBtn.setPrefHeight(61);
        admitEmployeeBtn.setPrefWidth(234);
        admitEmployeeBtn.setVisible(true);
        admitEmployeeBtn.setOnAction(e -> {
            try {
                admitEmployee();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });

        AnchorPane.setTopAnchor(editEmployeeBtn, 381.0);
        AnchorPane.setLeftAnchor(editEmployeeBtn, 487.0);
        editEmployeeBtn.setPrefHeight(61);
        editEmployeeBtn.setPrefWidth(234);
        editEmployeeBtn.setVisible(false);
        editEmployeeBtn.setOnAction(e -> {
            try {
                editEmployee();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });

        AnchorPane.setTopAnchor(searchEmployeeBtn, 381.0);
        AnchorPane.setLeftAnchor(searchEmployeeBtn, 487.0);
        searchEmployeeBtn.setPrefHeight(61);
        searchEmployeeBtn.setPrefWidth(234);
        searchEmployeeBtn.setVisible(true);
        searchEmployeeBtn.setOnAction(e -> {
            try {
                searchEmployee();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });

        AnchorPane ap = new AnchorPane();
        ap.getChildren().addAll(employeeIDTF, employeeIDLbl,
                employeeNameTF, employeeNameLbl,
                employeeNationalIDTF, employeeNationalIDLbl,
                employeeAddressTF, employeeAddressLbl,
                employeeContact1TF, employeeContact1Lbl,
                employeeContact2TF, employeeContact2Lbl,
                employeeGenderCB, employeeGenderLbl,
                employeeDateOfBirthDP, employeeDateOfBirthLbl,
                employeeNotifLbl, admitEmployeeBtn,
                editEmployeeBtn, searchEmployeeBtn);

        Scene scene = new Scene(ap);
        employeeFormStage.setScene(scene);
        employeeFormStage.setHeight(530);
        employeeFormStage.setWidth(750);
        employeeFormStage.setResizable(false);
        employeeFormStage.initOwner(LWF.stage.getScene().getWindow());
        employeeFormStage.initModality(Modality.APPLICATION_MODAL);
        
        employeeFormStage.getIcons().add(new Image(LWF.class.getResourceAsStream("logo.png")));
        
    }

    public void loadEmployeeSearchForm() {

        if (!isEmployeeFormLoaded) {

            loadEmployeeForm();

            isEmployeeFormLoaded = true;
        }
        
        employeeFormStage.setTitle("Employee Search Form");
        employeeIDTF.setVisible(true);
        employeeIDLbl.setVisible(true);
        searchEmployeeBtn.setVisible(true);
        editEmployeeBtn.setVisible(false);
        admitEmployeeBtn.setVisible(false);

        clearEmployeeForm();

        employeeFormStage.show();
    }

    public void searchEmployee() throws ClassNotFoundException, ClassNotFoundException, SQLException {

        int fieldCount = 0;
        String conditions = "";

        String employeeID = employeeIDTF.getText().trim();
        if (employeeID.compareTo("") != 0) {
            if (fieldCount == 0) {
                conditions = conditions + "employee_ID = " + employeeID + "";
                fieldCount++;
            } else {
                conditions = conditions + " AND employee_ID = " + employeeID + "";
                fieldCount++;
            }
        }

        String name = employeeNameTF.getText().trim();
        if (name.compareTo("") != 0) {
            if (fieldCount == 0) {
                conditions = conditions + "name = '" + name + "'";
                fieldCount++;
            } else {
                conditions = conditions + " AND name = '" + name + "'";
                fieldCount++;
            }
        }

        String nationalID = employeeNationalIDTF.getText().trim();
        if (nationalID.compareTo("") != 0) {
            if (fieldCount == 0) {
                conditions = conditions + "national_id = " + nationalID + "";
                fieldCount++;
            } else {
                conditions = conditions + " AND national_id = " + nationalID + "";
                fieldCount++;
            }
        }

        String address = employeeAddressTF.getText().trim();
        if (address.compareTo("") != 0) {
            if (fieldCount == 0) {
                conditions = conditions + "address = '" + address + "'";
                fieldCount++;
            } else {
                conditions = conditions + " AND address = '" + address + "'";
                fieldCount++;
            }
        }

        String contact1 = employeeContact1TF.getText().trim();
        if (contact1.compareTo("") != 0) {
            if (fieldCount == 0) {
                conditions = conditions + "contact1 = " + contact1 + "";
                fieldCount++;
            } else {
                conditions = conditions + " AND contact1 = " + contact1 + "";
                fieldCount++;
            }
        }

        String contact2 = employeeContact2TF.getText().trim();
        if (contact2.compareTo("") != 0) {
            if (fieldCount == 0) {
                conditions = conditions + "contact2 = " + contact2 + "";
                fieldCount++;
            } else {
                conditions = conditions + " AND contact2 = " + contact2 + "";
                fieldCount++;
            }
        }

        LocalDate dateOfBirthLD = employeeDateOfBirthDP.getValue();

        String dateOfBirth = "";
        if (dateOfBirthLD != null) {

            dateOfBirth = dateOfBirthLD.toString();

            if (fieldCount == 0) {

                conditions = conditions + "date_of_birth = '" + dateOfBirth + "'";
                fieldCount++;
            } else {
                conditions = conditions + " AND date_of_birth = '" + dateOfBirth + "'";
                fieldCount++;
            }
        }

        String gender = "";
        if (employeeGenderCB.getSelectionModel().getSelectedItem() != null) {
            gender = employeeGenderCB.getSelectionModel().getSelectedItem();
            if (fieldCount == 0) {
                conditions = conditions + "gender = '" + gender + "'";
                fieldCount++;
            } else {
                conditions = conditions + " AND gender = '" + gender + "'";
                fieldCount++;
            }
        }
        
        if (fieldCount == 0) {
            return;
        }

        String selected = ((RadioButton) employeeTypeTG.getSelectedToggle()).getText();

        String SQL = "";
        String SQLCount = "";

        if (selected.compareTo("All") == 0) {

            SQL = "select * from employee where " + conditions + ";";
            SQLCount = "select count(*) from employee where " + conditions + ";";
            loadQuery(SQL, SQLCount, employeeTable, 128);
            
            employeeFormStage.close();

        } else if (selected.compareTo("Teachers") == 0) {

            SQL = "select * from (select * from employee where employee_ID in (select teacher_id from teacher)) where " + conditions + ";";
            SQLCount = "select count(*) from (select * from employee where employee_ID in (select teacher_id from teacher)) where " + conditions + ";";
            loadQuery(SQL, SQLCount, employeeTable, 128);

            employeeFormStage.close();

        } else if (selected.compareTo("Managers") == 0) {

            SQL = "select * from (select * from employee where employee_ID in (select manager_id from manager)) where " + conditions + ";";
            SQLCount = "select count(*) from (select * from employee where employee_ID in (select manager_id from manager)) where " + conditions + ";";
            loadQuery(SQL, SQLCount, employeeTable, 128);

            employeeFormStage.close();

        } else if (selected.compareTo("Secretaries") == 0) {

            SQL = "select * from (select * from employee where employee_ID in (select secretary_id from secretary)) where " + conditions + ";";
            SQLCount = "select count(*) from (select * from employee where employee_ID in (select secretary_id from secretary)) where " + conditions + ";";
            loadQuery(SQL, SQLCount, employeeTable, 128);

            employeeFormStage.close();

        }

    }

    public void clearEmployeeSearch() throws ClassNotFoundException, SQLException {

        String selected = ((RadioButton) employeeTypeTG.getSelectedToggle()).getText();

        if (selected.compareTo("All") == 0) {
            loadEmployees();
        } else if (selected.compareTo("Teachers") == 0) {
            loadTeachers();
        } else if (selected.compareTo("Secretaries") == 0) {
            loadSecretaries();
        } else if (selected.compareTo("Managers") == 0) {
            loadManagers();
        }
    }

    public void loadTeachers() throws ClassNotFoundException, SQLException {

        String query = "select * from employee where employee_ID in (select teacher_id from teacher);";
        String queryCount = "select count(*) from employee where employee_ID in (select teacher_id from teacher);";

        loadQuery(query, queryCount, employeeTable, 128);
    }

    public void loadSecretaries() throws ClassNotFoundException, SQLException {

        String query = "select * from employee where employee_ID in (select secretary_id from secretary);";
        String queryCount = "select count(*) from employee where employee_ID in (select secretary_id from secretary);";

        loadQuery(query, queryCount, employeeTable, 128);

    }

    public void loadManagers() throws ClassNotFoundException, SQLException {

        String query = "select * from employee where employee_ID in (select manager_id from manager);";
        String queryCount = "select count(*) from employee where employee_ID in (select manager_id from manager);";

        loadQuery(query, queryCount, employeeTable, 128);

    }

    public void setEmployeeType() throws ClassNotFoundException, SQLException {

        if (employeeTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        if (employeeTypeCB.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        String type = employeeTypeCB.getSelectionModel().getSelectedItem();
        String ID = employeeTable.getSelectionModel().getSelectedItem()[0];
        String query = "";

        if (type.compareTo("Teacher") == 0) {

            query = "insert into teacher(teacher_id) values (" + ID + ");";

        } else if (type.compareTo("Manager") == 0) {

            query = "insert into Manager(manager_id) values (" + ID + ");";

        } else if (type.compareTo("Secretary") == 0) {

            query = "insert into Secretary(secretary_id) values (" + ID + ");";

        }

        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        Statement stmt = con.createStatement();

        stmt.executeUpdate(query);

        stmt.close();
        con.close();

    }

    public void loadStudentYearsSearch() throws ClassNotFoundException, SQLException {
        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        yearOL = FXCollections.observableArrayList();

        //Get current list of professions and add it to studentProfessionCB
        String SQL2 = "Select class_year from class;";
        Statement stmt2 = con.createStatement();
        ResultSet rs2 = stmt2.executeQuery(SQL2);

        //Add professions to studentProfessionCB
        while (rs2.next()) {
            yearOL.add(rs2.getString(1));
        }
        studentYearCB.setItems(yearOL);

        //Close connection
        rs2.close();
        stmt2.close();
        con.close();
    }

    public void clearStudentForm() {

        studentIDTF.setText("");
        studentNameTF.setText("");
        studentGuardianNameTF.setText("");
        studentNationalIDTF.setText("");
        studentAddressTF.setText("");
        studentContact1TF.setText("");
        studentContact2TF.setText("");
        studentProfessionCB.getSelectionModel().clearSelection();
        studentGenderCB.getSelectionModel().clearSelection();
        studentDateOfBirthDP.setValue(null);

    }

    public void loadStudentSearchForm() throws ClassNotFoundException, SQLException {

        loadStudentProfessions();

        loadStudentYearsSearch();

        if (cheapCount == 0) {
            loadStudentForm();
            cheapCount++;
        }

        studentYearCB.setVisible(true);
        studentYearLbl.setVisible(true);
        studentIDTF.setVisible(true);
        studentIDLbl.setVisible(true);
        searchStudentBtn.setVisible(true);
        admitStudentBtn.setVisible(false);
        editStudentBtn.setVisible(false);

        clearStudentForm();
        
        studentFormStage.setTitle("Student Search Form");
        
        studentFormStage.show();
    }

    public void searchStudent() throws ClassNotFoundException, SQLException {

        int fieldCount = 0;
        String conditions = "";

        String studentID = studentIDTF.getText().trim();
        if (studentID.compareTo("") != 0) {
            if (fieldCount == 0) {
                conditions = conditions + "student_ID = " + studentID + "";
                fieldCount++;
            } else {
                conditions = conditions + " AND student_ID = " + studentID + "";
                fieldCount++;
            }
        }

        String name = studentNameTF.getText().trim();
        if (name.compareTo("") != 0) {
            if (fieldCount == 0) {
                conditions = conditions + "name = '" + name + "'";
                fieldCount++;
            } else {
                conditions = conditions + " AND name = '" + name + "'";
                fieldCount++;
            }
        }

        String guardianName = studentGuardianNameTF.getText().trim();
        if (guardianName.compareTo("") != 0) {
            if (fieldCount == 0) {
                conditions = conditions + "guardian_name = '" + guardianName + "'";
                fieldCount++;
            } else {
                conditions = conditions + " AND guardian_name = '" + guardianName + "'";
                fieldCount++;
            }
        }

        if (studentProfessionCB.getSelectionModel().getSelectedItem() != null) {

            String professionName = studentProfessionCB.getSelectionModel().getSelectedItem();
            String professionID = getProfessionID(professionName);
            if (fieldCount == 0) {
                conditions = conditions + "profession_ID = " + professionID;
                fieldCount++;
            } else {
                conditions = conditions + " AND profession_ID = " + professionID;
                fieldCount++;
            }
        }

        String nationalID = studentNationalIDTF.getText().trim();
        if (nationalID.compareTo("") != 0) {
            if (fieldCount == 0) {
                conditions = conditions + "national_id = " + nationalID + "";
                fieldCount++;
            } else {
                conditions = conditions + " AND national_id = " + nationalID + "";
                fieldCount++;
            }
        }

        String address = studentAddressTF.getText().trim();
        if (address.compareTo("") != 0) {
            if (fieldCount == 0) {
                conditions = conditions + "address = '" + address + "'";
                fieldCount++;
            } else {
                conditions = conditions + " AND address = '" + address + "'";
                fieldCount++;
            }
        }

        String contact1 = studentContact1TF.getText().trim();
        if (contact1.compareTo("") != 0) {
            if (fieldCount == 0) {
                conditions = conditions + "contact1 = " + contact1 + "";
                fieldCount++;
            } else {
                conditions = conditions + " AND contact1 = " + contact1 + "";
                fieldCount++;
            }
        }

        String contact2 = studentContact2TF.getText().trim();
        if (contact2.compareTo("") != 0) {
            if (fieldCount == 0) {
                conditions = conditions + "contact2 = " + contact2 + "";
                fieldCount++;
            } else {
                conditions = conditions + " AND contact2 = " + contact2 + "";
                fieldCount++;
            }
        }

        LocalDate dateOfBirthLD = studentDateOfBirthDP.getValue();

        if (dateOfBirthLD != null) {

            String dateOfBirth = dateOfBirthLD.toString();
            if (fieldCount == 0) {

                conditions = conditions + "date_of_birth = '" + dateOfBirth + "'";
                fieldCount++;
            } else {
                conditions = conditions + " AND date_of_birth = '" + dateOfBirth + "'";
                fieldCount++;
            }
        }

        String gender = "";
        if (studentGenderCB.getSelectionModel().getSelectedItem() != null) {
            gender = studentGenderCB.getSelectionModel().getSelectedItem();
            if (fieldCount == 0) {
                conditions = conditions + "gender = '" + gender + "'";
                fieldCount++;
            } else {
                conditions = conditions + " AND gender = '" + gender + "'";
                fieldCount++;
            }
        }

        String year = "";
        if (studentYearCB.getSelectionModel().getSelectedItem() != null) {
            year = (String) (studentYearCB.getSelectionModel().getSelectedItem());
            if (fieldCount == 0) {
                conditions = conditions + "class_year = '" + year + "'";
                fieldCount++;
            } else {
                conditions = conditions + " AND class_year = '" + year + "'";
                fieldCount++;
            }
        }

        if (fieldCount == 0) {
            return;
        }

        String SQL = "select * from student where " + conditions + ";";
        String SQLCount = "select count(*) from student where " + conditions + ";";
        loadStudentQuery(SQL, SQLCount, studentTable, 140);

        studentFormStage.close();

    }

    public void setGrade() throws ClassNotFoundException, SQLException {

        String selected = ((RadioButton) subjectInfoTG.getSelectedToggle()).getText();
        if (selected.compareTo("Students") != 0) {
            return;
        }

        if (subjectInfoTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        String subjectID = getSubjectID(subjectTable.getSelectionModel().getSelectedItem()[0]);

        String studentID = subjectInfoTable.getSelectionModel().getSelectedItem()[0];

        for (int i = 0; i < setGradeTF.getText().length(); i++) {
            if (!Character.isDigit(setGradeTF.getText().charAt(i))) {

                fadeTransition(setGradeNotifLbl, "Invalid Grade");
                return;
            }
        }

        int setGrade = Integer.parseInt(setGradeTF.getText());

        String getTotalGrade = "select total_grade from subject where subject_id = " + subjectID + ";";

        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery(getTotalGrade);

        rs.next();

        int totalGrade = Integer.parseInt(rs.getString(1));

        if (setGrade > totalGrade) {

            fadeTransition(setGradeNotifLbl, "Set grade cannot be greater than total grade");

            rs.close();
            stmt.close();
            con.close();
            return;
        }

        String updateGrade = "update enroll set grade = " + setGrade + " where student_id = " + studentID + " AND subject_ID = " + subjectID + ";";

        Statement stmt2 = con.createStatement();

        stmt2.executeUpdate(updateGrade);

        rs.close();
        stmt.close();
        con.close();

        String queryCount = "select count(*) from enroll e, student s where e.student_id = s.student_id AND e.subject_id = " + subjectID + ";";
        String query = "select s.Student_ID, s.Name,e.Grade from enroll e, student s where e.student_id = s.student_id AND e.subject_id = " + subjectID + ";";

        loadQuery(query, queryCount, subjectInfoTable, 140);

        getSubjectAverage();
    }

    public void removeTeacher() throws ClassNotFoundException, SQLException {

        if (subjectInfoTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        String subjectID = getSubjectID(subjectTable.getSelectionModel().getSelectedItem()[0]);
        String teacherID = subjectInfoTable.getSelectionModel().getSelectedItem()[0];

        String removeTeacherFromSubject = "delete from teaching where subject_id = " + subjectID + " AND teacher_id = " + teacherID + ";";

        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        Statement stmt = con.createStatement();

        stmt.executeUpdate(removeTeacherFromSubject);

        stmt.close();
        con.close();

        String queryCount = "select count(*) from teaching te,teacher t where te.teacher_ID = t.teacher_ID AND te.subject_id = " + subjectID + ";";
        String query = "select Employee_ID, Name from teaching te,employee t where te.teacher_ID = t.employee_ID AND te.subject_id = " + subjectID + ";";

        loadQuery(query, queryCount, subjectInfoTable, 210);
    }

    public void loadYears() throws SQLException, ClassNotFoundException {

        yearOL = FXCollections.observableArrayList();

        //Establish connection to database
        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        //Get current list of professions and add it to studentProfessionCB
        String SQL = "Select class_year from class;";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        //Add professions to studentProfessionCB
        while (rs.next()) {
            yearOL.add(rs.getString(1));
        }
        subjectYearCB.setItems(yearOL);

        //Close connection
        rs.close();
        stmt.close();
        con.close();

        subjectYearCB.getSelectionModel().select(0);

    }

    public void loadStudentYears() throws SQLException, ClassNotFoundException {

        yearOL = FXCollections.observableArrayList();

        //Establish connection to database
        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        //Get current list of professions and add it to studentProfessionCB
        String SQL = "Select class_year from class;";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        //Add professions to studentProfessionCB
        while (rs.next()) {
            yearOL.add(rs.getString(1));
        }
        studentYearSelectCB.setItems(yearOL);

        //Close connection
        rs.close();
        stmt.close();
        con.close();

        studentYearSelectCB.getSelectionModel().select(0);

    }

    public void filterSubjectStudents() throws ClassNotFoundException, SQLException {

        if (subjectYearCB.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        if (subjectTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        String year = (String) (subjectYearCB.getSelectionModel().getSelectedItem());
        String subjectID = getSubjectID(subjectTable.getSelectionModel().getSelectedItem()[0]);

        String query = "select s.Student_ID, s.Name, e.Grade from (select * from student where class_year = " + year + ") s,enroll e where subject_ID = " + subjectID + " AND s.student_id = e.student_id;";
        String queryCount = "select count(*) from (select * from student where class_year = " + year + ") s,enroll e where subject_ID = " + subjectID + " AND s.student_id = e.student_id;";

        loadQuery(query, queryCount, subjectInfoTable, 140);

    }

    public void getSubjectAverage() throws ClassNotFoundException, SQLException {

        if (subjectTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        String subjectID = getSubjectID(subjectTable.getSelectionModel().getSelectedItem()[0]);

        String year = (String) (subjectYearCB.getSelectionModel().getSelectedItem());
        String query = "select avg(e.grade) from enroll e, student s where s.student_id = e.student_id AND s.class_year = " + year + " AND subject_ID = " + subjectID + ";";

        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        rs.next();

        if (rs.getString(1) == null) {

            rs.close();
            stmt.close();
            con.close();
            subjectAverageLbl.setText("");
            return;
        }

        double average = Double.parseDouble(rs.getString(1));

        rs.close();
        stmt.close();
        con.close();

        subjectAverageLbl.setText(String.format("%.2f", average));

    }

    public void getSubjectNumberOfStudents() throws SQLException, ClassNotFoundException {

        if (subjectTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        String subjectID = getSubjectID(subjectTable.getSelectionModel().getSelectedItem()[0]);

        String year = (String) (subjectYearCB.getSelectionModel().getSelectedItem());
        String query = "select count(*) from enroll e, student s where s.student_id = e.student_id AND s.class_year = " + year + " AND subject_ID = " + subjectID + ";";

        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        rs.next();

        String numberOfStudents = rs.getString(1);

        rs.close();
        stmt.close();
        con.close();

        subjectNumberOfStudentsLbl.setText(numberOfStudents);

    }

    public void addYear() throws ClassNotFoundException, SQLException {

        String addYear = "insert into class values();";

        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        Statement stmt = con.createStatement();
        stmt.executeUpdate(addYear);

        stmt.close();
        con.close();

        loadStudentYears();

        studentYearSelectCB.getSelectionModel().selectLast();

    }

    public void loadStudentGradesForm() {

        AnchorPane ap = new AnchorPane();

        ap.setPrefHeight(510.0);
        ap.setPrefWidth(458.0);

        studentGradesTable.setLayoutX(48.0);
        studentGradesTable.setLayoutY(42.0);
        studentGradesTable.setPrefHeight(401.0);
        studentGradesTable.setPrefWidth(360.0);

        semester1Lbl.setLayoutX(48.0);
        semester1Lbl.setLayoutY(455.0);
        semester1Lbl.setText("Semester 1:");

        semester2Lbl.setLayoutX(48.0);
        semester2Lbl.setLayoutY(476.0);
        semester2Lbl.setText("Semester 2:");

        totalLbl.setLayoutX(238.0);
        totalLbl.setLayoutY(455.0);
        totalLbl.setText("Total:");

        semester1AverageLbl.setLayoutX(132.0);
        semester1AverageLbl.setLayoutY(455.0);

        semester2AverageLbl.setLayoutX(132.0);
        semester2AverageLbl.setLayoutY(476.0);

        totalAverageLbl.setLayoutX(283.0);
        totalAverageLbl.setLayoutY(455.0);

        ap.getChildren().add(studentGradesTable);
        ap.getChildren().add(semester1Lbl);
        ap.getChildren().add(semester2Lbl);
        ap.getChildren().add(totalLbl);
        ap.getChildren().add(semester1AverageLbl);
        ap.getChildren().add(semester2AverageLbl);
        ap.getChildren().add(totalAverageLbl);

        Scene scene = new Scene(ap);
        
        
        studentGradesStage.setTitle("Grade Report");
        studentGradesStage.setResizable(false);
        studentGradesStage.getIcons().add(new Image(LWF.class.getResourceAsStream("logo.png")));
        studentGradesStage.initModality(Modality.APPLICATION_MODAL);
        studentGradesStage.setScene(scene);
    }

    boolean isStudentGradesFormLoaded = false;
    public void viewStudentGrades() throws ClassNotFoundException, SQLException {

        if (studentTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        if (!isStudentGradesFormLoaded) {
            loadStudentGradesForm();

            isStudentGradesFormLoaded = true;
        }

        String studentID = studentTable.getSelectionModel().getSelectedItem()[0];
        String studentGrades = "Select s.Name as Subject_Name, e.Grade, s.Total_Grade From Subject s, Enroll e where s.Subject_ID = e.Subject_ID AND e.Student_ID = " + studentID + " AND s.Subject_ID in (Select e2.Subject_ID From Enroll e2 where e2.Student_ID = " + studentID + ");";
        String studentGradesCount = "Select count(*) From Subject s, Enroll e where s.Subject_ID = e.Subject_ID AND e.Student_ID = " + studentID + " AND s.Subject_ID in (Select e2.Subject_ID From Enroll e2 where e2.Student_ID = " + studentID + ");";
        
        loadQuery(studentGrades, studentGradesCount, studentGradesTable, 120);

        String sumTotalGradesQuery = "Select sum(s.Total_Grade) From Subject s, Enroll e where s.Subject_ID = e.Subject_ID AND student_id = " + studentID +" AND s.Subject_ID in (Select e2.Subject_ID From Enroll e2 where e2.Student_ID = " + studentID + ");";
        String sumGradesQuery = "Select sum(e.Grade) From Subject s, Enroll e where s.Subject_ID = e.Subject_ID AND student_id = " + studentID +" AND s.Subject_ID in (Select e2.Subject_ID From Enroll e2 where e2.Student_ID = " + studentID + ");";

        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();

        Statement stmt1 = con.createStatement();
        ResultSet rs1 = stmt1.executeQuery(sumTotalGradesQuery);
        rs1.next();

        int sumTotalGrades = 0;
        if (rs1.getString(1) != null) {
            sumTotalGrades = Integer.parseInt(rs1.getString(1));
        }

        stmt1.close();
        rs1.close();

        Statement stmt2 = con.createStatement();
        ResultSet rs2 = stmt2.executeQuery(sumGradesQuery);
        rs2.next();

        int sumGrades = 0;
        if (rs2.getString(1) != null) {
            sumGrades = Integer.parseInt(rs2.getString(1));
        }

        rs2.close();
        stmt2.close();

        double totalAverage = 0;
        if (sumTotalGrades != 0) {
            totalAverage = ((double) sumGrades / (double) sumTotalGrades) * 100;
        }

        totalAverageLbl.setText(String.format("%.2f", totalAverage) + "%");

        String sumSemester1TotalGradesQuery = "Select sum(s.Total_Grade) From Subject s, Enroll e where s.Subject_ID = e.Subject_ID AND s.Semester = 1 AND e.Student_ID = " + studentID + " AND s.Subject_ID in (Select e2.Subject_ID From Enroll e2 where e2.Student_ID = " + studentID + ");";
        String sumSemester1GradesQuery = "Select sum(e.Grade) From Subject s, Enroll e where s.Subject_ID = e.Subject_ID AND s.Semester = 1 AND e.Student_ID = " + studentID + " AND s.Subject_ID in (Select e2.Subject_ID From Enroll e2 where e2.Student_ID = " + studentID + ");";

        Statement stmt3 = con.createStatement();
        ResultSet rs3 = stmt3.executeQuery(sumSemester1TotalGradesQuery);
        rs3.next();

        int sumSemester1TotalGrades = 0;
        if (rs3.getString(1) != null) {
            sumSemester1TotalGrades = Integer.parseInt(rs3.getString(1));
        }

        stmt3.close();
        rs3.close();

        Statement stmt4 = con.createStatement();
        ResultSet rs4 = stmt4.executeQuery(sumSemester1GradesQuery);
        rs4.next();

        int sumSemester1Grades = 0;
        if (rs4.getString(1) != null) {
            sumSemester1Grades = Integer.parseInt(rs4.getString(1));
        }

        rs4.close();
        stmt4.close();

        double semester1Average = 0;
        if (sumSemester1TotalGrades != 0) {
            semester1Average = ((double) sumSemester1Grades / (double) sumSemester1TotalGrades) * 100;
        }

        semester1AverageLbl.setText(String.format("%.2f", semester1Average) + "%");

        String sumSemester2TotalGradesQuery = "Select sum(s.Total_Grade) From Subject s, Enroll e where s.Subject_ID = e.Subject_ID AND s.Semester = 2 AND e.Student_ID = " + studentID + " AND s.Subject_ID in (Select e2.Subject_ID From Enroll e2 where e2.Student_ID = " + studentID + ");";
        String sumSemester2GradesQuery = "Select sum(e.Grade) From Subject s, Enroll e where s.Subject_ID = e.Subject_ID AND s.Semester = 2 AND e.Student_ID = " + studentID + " AND s.Subject_ID in (Select e2.Subject_ID From Enroll e2 where e2.Student_ID = " + studentID + ");";

        Statement stmt5 = con.createStatement();
        ResultSet rs5 = stmt5.executeQuery(sumSemester2TotalGradesQuery);
        rs5.next();

        int sumSemester2TotalGrades = 0;
        if (rs5.getString(1) != null) {
            sumSemester2TotalGrades = Integer.parseInt(rs5.getString(1));
        }

        stmt5.close();
        rs5.close();

        Statement stmt6 = con.createStatement();
        ResultSet rs6 = stmt6.executeQuery(sumSemester2GradesQuery);
        rs6.next();

        int sumSemester2Grades = 0;
        if (rs6.getString(1) != null) {
            sumSemester2Grades = Integer.parseInt(rs6.getString(1));
        }

        rs6.close();
        stmt6.close();

        double semester2Average = 0;
        if (sumSemester2TotalGrades != 0) {
            semester2Average = ((double) sumSemester2Grades / (double) sumSemester2TotalGrades) * 100;
        }

        semester2AverageLbl.setText(String.format("%.2f", semester2Average) + "%");

        con.close();

        studentGradesStage.show();
    }
    
    public void loadTuitionReportForm(){
        
        AnchorPane ap = new AnchorPane();

        ap.setPrefHeight(400.0);
        ap.setPrefWidth(672.0);

        unpaidTable.setLayoutX(57.0);
        unpaidTable.setLayoutY(47.0);
        unpaidTable.setPrefHeight(244.0);
        unpaidTable.setPrefWidth(554.0);

        expectedLbl.setLayoutX(57.0);
        expectedLbl.setLayoutY(306.0);
        expectedLbl.setText("Expected:");

        requirementLbl.setLayoutX(57.0);
        requirementLbl.setLayoutY(380.0);
        requirementLbl.setText("Required:");
        
        requiredAmountLbl.setLayoutX(131);
        requiredAmountLbl.setLayoutY(380);
        
        paymentLbl.setLayoutX(57.0);
        paymentLbl.setLayoutY(343.0);
        paymentLbl.setText("Paid:");

        expectedAmountLbl.setLayoutX(131.0);
        expectedAmountLbl.setLayoutY(306.0);
        
        unpaidTableLbl.setLayoutX(56.0);
        unpaidTableLbl.setLayoutY(26.0);
        
        paidAmountLbl.setLayoutX(131.0);
        paidAmountLbl.setLayoutY(343.0);

        ap.getChildren().add(unpaidTable);
        ap.getChildren().add(expectedLbl);
        ap.getChildren().add(paymentLbl);
        ap.getChildren().add(expectedAmountLbl);
        ap.getChildren().add(paidAmountLbl);
        ap.getChildren().add(unpaidTableLbl);
        ap.getChildren().add(requiredAmountLbl);
        ap.getChildren().add(requirementLbl);
        
        Scene scene = new Scene(ap);
        
        tuitionReportStage.setTitle("Tuition Report");
        tuitionReportStage.initModality(Modality.APPLICATION_MODAL);
        tuitionReportStage.setResizable(false);
        tuitionReportStage.getIcons().add(new Image(LWF.class.getResourceAsStream("logo.png")));
        tuitionReportStage.setScene(scene);
    }
    
    boolean isTuitionReportFormLoaded = false;
    public void tuitionReport() throws ClassNotFoundException, SQLException{
        
        if(!isTuitionReportFormLoaded){
            loadTuitionReportForm();
            isTuitionReportFormLoaded = true;
        }
        
        loadTuitionReportValues();
        
        tuitionReportStage.show();
        
    }
    
    public void loadTuitionReportValues() throws ClassNotFoundException, SQLException{
        
        String year = (String)(studentYearSelectCB.getSelectionModel().getSelectedItem());
        
        String unpaidStudents = "select s.Student_ID, s.Name, t.Amount_Paid, t.Amount_Required from student s, tuition t where s.student_id = t.student_id AND t.Amount_Required != 0 AND s.class_year = " + year + ";";
        String unpaidStudentsCount = "select count(*) from student s, tuition t where s.student_id = t.student_id AND t.amount_required != 0 AND s.class_year = " + year + ";";
        loadQuery(unpaidStudents,unpaidStudentsCount,unpaidTable,140);
        
        String expectedAmountQuery = "select sum(t.amount_paid + t.amount_required) from tuition t, student s where s.student_id = t.student_id AND s.class_year = " + year + ";";
        String paidAmountQuery = "select sum(t.amount_paid) from tuition t, student s where s.student_id = t.student_id AND s.class_year = " + year + ";";
        String requiredAmountQuery = "select sum(t.amount_required) from tuition t, student s where s.student_id = t.student_id AND s.class_year = " + year + ";";
        
        Database a = new Database(URL, port, dbName, dbUsername, dbPassword);
        con = a.connectDB();
        
        Statement stmt1 = con.createStatement();
        ResultSet rs1 = stmt1.executeQuery(expectedAmountQuery);
        
        rs1.next();
        
        String expectedAmount = "0";
        if(rs1.getString(1) != null){
            expectedAmount = rs1.getString(1);
        }
        
        rs1.close();
        stmt1.close();
        
        
        Statement stmt2 = con.createStatement();
        ResultSet rs2 = stmt2.executeQuery(paidAmountQuery);
        
        rs2.next();
        
        String paidAmount = "0";
        if(rs2.getString(1) != null){
            paidAmount = rs2.getString(1);
        }
        
        rs2.close();
        stmt2.close();
        
        Statement stmt3 = con.createStatement();
        ResultSet rs3 = stmt3.executeQuery(requiredAmountQuery);
        
        rs3.next();
        
        String requiredAmount = "0";
        if(rs3.getString(1) != null){
            requiredAmount = rs3.getString(1);
        }
        
        rs3.close();
        stmt3.close();
        
        paidAmountLbl.setText(paidAmount + " NIS");
        expectedAmountLbl.setText(expectedAmount + " NIS");
        requiredAmountLbl.setText(requiredAmount + " NIS");
        
    }
    
}
