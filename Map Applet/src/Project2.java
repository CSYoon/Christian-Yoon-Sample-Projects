
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Project2 extends JApplet implements MouseListener, ActionListener, ItemListener
{
    private final static int MAXOBJECTS = 100;  // max number of objects allowed in list
    private final static int MAXPOINTS = 5;   // max number of boundary points per object

    private int[] xCoords;              // x coords of object being drawn
    private int[] yCoords;              // y coords of object being drawn
    private boolean draw;               // if allowed to draw or not
    private int ptCount;                // number of pts contained in an object
    private int objCount;               // number of map objects
    private MapObjects[] list;          // array of map objects contained in the map
    private State[] myStates;           // array of states in the map
    private JButton addPlaces;          // button to add places
    private JButton editPop;            // button to edit population
    private JButton savePlace;          // button to save object being drawn
    private JButton savePop;            // save population while editing
    private JButton defineGeo;          // define boundary of object
    private JCheckBox showDetail;       // show details of a map object
    private JCheckBox colorCode;        // highlight a color of a state
    private JLabel nameDescription;     // description of where to put in name of object
    private JLabel pickState;           // description of where to pick selection
    private JLabel population;          // description of where population is
    private JTextField editPopNum;      // textfield for edit population
    private JTextField popNumber;       // textfield for bottom panel
    private JTextField name;            // textfield for adding object
    private JRadioButton state;         // button to select state
    private JRadioButton lake;          // button to select lake
    private ButtonGroup type;           // button group to ensure only 1 of the 2 JRadioButtons
                                        //  are selected
    private JComboBox stateList;        // drop down menu of states
    private JPanel topPanel;            // GUI components at the furthest top
    private JPanel menu;                // GUI components of of two basic selection
    private JPanel addition;            // GUI components pertaining to adding object to map
    private JPanel bottomPanel;         // GUI components on bottom panel
    private JPanel edPop;               // GUI components pertaining to editing population
    private boolean stateOrNot;         // determine if it's a state or not
    private boolean details;            // show details or not
    private boolean color;              // color object or not
    private State stateSelected;        // state selected in array of states
    private int stateIndex;             // index of state selected
    private int drawingCanvas;          // size of the actual drawing canvas


    private BorderLayout layout;

    @Override
    public void init()
    {
        stateSelected = new State();            // initialize class to hold state selected
                                                //  from array

        drawingCanvas = 0;                      // height of applet subtracting heights of panels

        layout = new BorderLayout();            // layout to set components north and south
        setLayout(layout);                      // sets layout to applet

        objCount = 0;                           //
        ptCount = 0;                            // count of points initially 0
        list = new MapObjects[MAXOBJECTS];      // initialize array size of how many objects in map
        xCoords = new int[MAXPOINTS];           // initialize array to hold x components
        yCoords = new int[MAXPOINTS];           // initialize array to hold y components

        draw = false;                           // initially can't draw
        color = false;                          // initially don't color in states

        stateOrNot = true;                      // initially drawing a state
        stateIndex = 0;                         // initially index at 0

        addTopPanel();                          // add top panel to applet
        addBotPanel();                          // add bottom panel to applet
    }

    public void addTopPanel()
    // POST: initializes and adds the panels incorporated with the top of the applet
    {
        initTopPrimary();                           // creates panel to add place or edit population

        initTopSecondaries();                       // creates panel when add place or
                                                    //  edit population is pressed

        topPanel = new JPanel(new BorderLayout());  // layout pertaining to just the top panel

        topPanel.remove(edPop);                     // edit population panel isn't initially shown
        topPanel.remove(addition);                  // add place panel isn't initially shown
        topPanel.add(menu,BorderLayout.NORTH);      // default menu is shown

        add(topPanel, BorderLayout.NORTH);          // adds panel to applet
    }

    public void initTopPrimary()
    // POST: creates buttons to add place or edit population
    {
        addPlaces = new JButton ("Add Places to Map");  // creates button to add place
        editPop = new JButton ("Edit Population");      // creates button to edit population

        // adds event handler when button is pressed
        addPlaces.addActionListener(this);
        editPop.addActionListener(this);

        // creates a new panel and adds the two buttons as components
        menu = new JPanel();
        menu.add(addPlaces);
        menu.add(editPop);
    }

    public void initTopSecondaries()
    // POST: creates panels that are displayed when add place or edit population button is pressed
    {
        initAddObject();    // creates all components associated with adding a place
        initEditPop();      // creates all components associated with editing population
    }

    public void initAddObject()
    // POST: creates all GUI components pertaining to when add place button is pressed
    {
        // creates label for text field to input name of object
        nameDescription = new JLabel("Name of new place: ");

        // creates textfield of where user inputs name and adds event handler
        name = new JTextField (20);
        name.addActionListener(this);

        // creates radio buttons so user can choose between adding a state or a lake
        state = new JRadioButton("State",true);
        lake = new JRadioButton("Lake", false);

        // adds event handler for radio buttons
        state.addItemListener(this);
        lake.addItemListener(this);

        // groups radio buttons together, so that only one can be choosen
        type = new ButtonGroup();
        type.add(state);
        type.add(lake);

        //creates 
        savePlace = new JButton("Save New Place");
        defineGeo = new JButton("Define Geography");
        savePlace.addActionListener(this);
        defineGeo.addActionListener(this);

        addition = new JPanel();
        addition.add(nameDescription);
        addition.add(name);
        addition.add(state);
        addition.add(lake);
        addition.add(defineGeo);
    }

    public void initEditPop()
    {
        pickState = new JLabel("Pick State");
        stateList = new JComboBox(GetStates());
        stateList.addItemListener(this);
        population = new JLabel("Population(in thousands of people):");
        editPopNum = new JTextField(10);
        editPopNum.addActionListener(this);
        savePop = new JButton("Save Population");
        savePop.addActionListener(this);

        edPop = new JPanel();
        edPop.add(pickState);
        edPop.add(stateList);
        edPop.add(population);
        edPop.add(editPopNum);
        edPop.add(savePop);

        validate();
    }

    public void addBotPanel()
    {
        showDetail = new JCheckBox("Show detail of places");
        colorCode = new JCheckBox("Color code places with populations at least");
        showDetail.addItemListener(this);
        colorCode.addItemListener(this);

        popNumber = new JTextField(10);
        popNumber.addActionListener(this);

        bottomPanel = new JPanel();
        bottomPanel.add(showDetail);
        bottomPanel.add(colorCode);
        bottomPanel.add(popNumber);

        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

        drawingCanvas = (getHeight()-topPanel.getHeight()-bottomPanel.getHeight());

        g.setColor(new Color(0,191,255));

        g.fillRect(0, topPanel.getHeight(), getWidth(), drawingCanvas);


        for (int ct = 0; ct < objCount; ct++ )
        {
            if (color)
            {
                if (list[ct] instanceof State)
                {
                    if (((State)list[ct]).GetPopulation() >= Integer.parseInt(popNumber.getText()))
                    {
                        ((State)list[ct]).SetColor(Color.ORANGE);
                        list[ct].Draw(g, getHeight(), getWidth());
                    }
                    else
                    {
                        list[ct].Draw(g, getHeight(), getWidth());
                    }
                }
                else
                {
                    list[ct].Draw(g, getHeight(), getWidth());
                }
            }
            else
            {
                if (list[ct] instanceof State)
                {
                ((State)list[ct]).SetColor(Color.GREEN);
                list[ct].Draw(g, getHeight(), getWidth());
                }
                else
                {
                    list[ct].Draw(g, getHeight(), getWidth());
                }
            }

            if (details)
            {
                list[ct].ShowDetails(g, getHeight(), getWidth());
            }
        }

        // draw boundary lines of current object being added.
        if (draw)
        {
            g.setColor(Color.BLACK);
            g.drawPolyline(xCoords, yCoords, ptCount);
        }
    }

    public State[] GetStates()
    // POST: returns an array of States from the initial list that contains both lakes and states.
    {
        int counter = 0;                                // counter starts at 0

        myStates = new State[stateIndex];               // initialize new array the size of however
                                                        //  many states
        for (int ct = 0; ct < objCount; ct++)
        {
            if (list[ct] instanceof State)
            {
                myStates[counter] = (State)list[ct];

                counter++;
            }
        }

        return myStates;
    }

    public void ResetPts()
    {
        removeMouseListener(this);
        xCoords = new int[MAXPOINTS];
        yCoords = new int[MAXPOINTS];
    }

    public void AddPlace()
    {
        topPanel.remove(edPop);
        topPanel.add(addition);

        ResetPts();
        
        validate();
    }

    public void EditPop()
    {
        topPanel.remove(addition);
        topPanel.remove(edPop);

        initEditPop();
        topPanel.add(edPop);

        validate();
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == addPlaces)
        {
            AddPlace();
        }

        if (e.getSource() == editPop)
        {
            EditPop();

            validate();
        }

        if (e.getSource() == defineGeo)
        {
            addition.remove(defineGeo);
            addition.add(savePlace);
            addMouseListener(this);

            draw = true;
            repaint();
        }

        if (e.getSource() == savePlace)
        {
            SavePlace();
        }

        if (e.getSource() == savePop)
        {
            SavePop();
        }
        
        repaint();
        validate();
    }

    public void SavePop()
    {
        int parseTest;
        
        try
        {
            parseTest = Integer.parseInt(editPopNum.getText());

            if (parseTest < 0)
            {
                JOptionPane.showMessageDialog(null, "Please input a value "
                        + "greater than or equal to 0", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                for (int ct = 0; ct < objCount; ct++)
                {
                    if (list[ct].GetName().contains(stateSelected.GetName()))
                    {
                        ((State)list[ct]).SetPop(Integer.parseInt(editPopNum.getText()));
                    }
                }
            }
            remove(topPanel);
            addTopPanel();
            validate();
        }
        catch (NumberFormatException nfe)
        {
            JOptionPane.showMessageDialog(null,"Please input an "
                    + "integer value to represent the population of the state you're editing"
                    + "", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void SavePlace()
    {
        if (ptCount <= 2)
        {
            JOptionPane.showMessageDialog(null, "Need to add more pts to boundary", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            if (stateOrNot)
            {
                list[objCount] = new State(name.getText(), xCoords, yCoords, getHeight(), getWidth(), ptCount);
                list[objCount].CalculateCenter();
                list[objCount].CalculateScalePoints();
                stateIndex++;
            }
            else
            {
                list[objCount] = new Lake(name.getText(), xCoords, yCoords, getHeight(), getWidth(), ptCount);
                list[objCount].CalculateCenter();
                list[objCount].CalculateScalePoints();
            }

            removeMouseListener(this);

            objCount++;
            ptCount = 0;
            draw = false;

            addition.add(defineGeo);
            addition.remove(savePlace);
            topPanel.remove(addition);
        }
    }

    public void mouseClicked(MouseEvent e)
    {
        // could be used, but mouseReleased is better in this case, because wherever you release
        //  the mouse, that's where you want the line to be drawn to.  Has to be included,
        //  due to implementation of of MouseListener (interface) and all
        //  methods of the interface has to be included
    }

    public void mousePressed(MouseEvent e)
    {
        // since we are inputting single points, pressing the mouse would not trigger anything
        //  but has to be included due to implementation of MouseListener(interface) and all
        //  methods of the interface has to be included
    }

    public void mouseReleased(MouseEvent e)
    {
        try
        {
            if (e.getY() > topPanel.getHeight() && e.getY() < (getHeight()-bottomPanel.getHeight()))
            {
                xCoords[ptCount] = e.getX();
                yCoords[ptCount] = e.getY();

                ptCount ++;
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Please select a point inside the "
                        + "drawing canvas","Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (ArrayIndexOutOfBoundsException aioobe)
        {
            JOptionPane.showMessageDialog(this, "You've reached the maximum pts to "
                    + "define this boundary");  
        }

        repaint();
    }

    public void mouseEntered(MouseEvent e)
    {
        // nothing happens when mouse enters applet, but has to included due
        //  to implementation of MouseListener(interface) and all methods of an interface has to
        //  included
    }

    public void mouseExited(MouseEvent e)
    {
        // nothing happens when mouse exits applet, but has to included due
        //  to implementation of MouseListener(interface) and all methods of an interface has to
        //  included
    }

    public void itemStateChanged(ItemEvent e)
    {  
       if (e.getSource() == state && e.getStateChange() == ItemEvent.SELECTED)
       {
           stateOrNot = true;
       }

       if (e.getSource() == lake && e.getStateChange() == ItemEvent.SELECTED)
       {
           stateOrNot = false;
       }

       if (e.getSource() == showDetail && e.getStateChange() == ItemEvent.SELECTED)
       {
           details = true;
       }
       else if (e.getSource() == showDetail && e.getStateChange() == ItemEvent.DESELECTED)
       {
           details = false;
       }

       if (e.getSource() == colorCode && e.getStateChange() == ItemEvent.SELECTED)
       {
           Filter();
       }
       else if (e.getSource() == colorCode && e.getStateChange() == ItemEvent.DESELECTED)
       {
           color = false;
       }

       if(e.getSource() == stateList)
       {
           stateSelected = ((State) stateList.getSelectedItem());
       }

       repaint();
    }

    public void Filter()
    {
        int parseTest;
        
        try
        {
            parseTest = Integer.parseInt(popNumber.getText());

            if (parseTest < 0)
            {
                JOptionPane.showMessageDialog(null, "Please input a value "
                        + "greater than or equal to 0", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                color = true;
            }
        }
        catch (NumberFormatException nfe)
        {
           JOptionPane.showMessageDialog(null,"Please input a population to filter"
                   + "","Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

