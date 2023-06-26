import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;


/* 
import org.w3c.dom.events.Event;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
 */


public class Memo extends JFrame{

    final int SHIFT_MASK          = 1 << 0;
    final int CTRL_MASK           = 1 << 1;
    final int META_MASK           = 1 << 2;
    final int ALT_MASK            = 1 << 3;


    JTextArea ta;
    JTextPane tp;
    File f;

    UndoManager undo_manager;
    public Memo(String title) {
        undo_manager=new UndoManager();
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenu m_file=new JMenu("파일(AF)");
        m_file.setMnemonic('F');
        //m_file.setMnemonic(KeyEvent.VK_F); // 단축키 Alt + F 설정
        JMenuItem m_new_note=new JMenuItem("새파일(N)");
        m_new_note.setAccelerator(KeyStroke.getKeyStroke('N', CTRL_MASK));
        JMenuItem m_new_window=new JMenuItem("새창(W)");
        m_new_window.setAccelerator(KeyStroke.getKeyStroke('W', CTRL_MASK));

        JMenuItem m_open=new JMenuItem("열기(O)");
        m_open.setAccelerator(KeyStroke.getKeyStroke('O', CTRL_MASK));
        JMenuItem m_save=new JMenuItem("저장(S)");
        m_save.setAccelerator(KeyStroke.getKeyStroke('S', CTRL_MASK));
        JMenuItem m_save_as=new JMenuItem("다른이름으로 저장(AS)");
        m_save_as.setAccelerator(KeyStroke.getKeyStroke('S', CTRL_MASK|SHIFT_MASK));
        // m_save_as.setMnemonic(KeyEvent.VK_F);
        JMenuItem m_print=new JMenuItem("인쇄(P)");
        m_print.setAccelerator(KeyStroke.getKeyStroke('P', CTRL_MASK));
        
        JMenuItem m_exit=new JMenuItem("끝내기");
        m_exit.setAccelerator(KeyStroke.getKeyStroke('X', CTRL_MASK));
        
        m_file.add(m_new_note);
        m_file.add(m_new_window);
        m_file.add(m_open);
        m_file.add(m_save);
        m_file.add(m_save_as);
        m_file.addSeparator();
        m_file.add(m_print);
        m_file.addSeparator();
        m_file.add(m_exit);
        
        
        // 편집
        JMenu m_edit=new JMenu("편집");
        
        ButtonGroup group = new ButtonGroup();
        
        JMenuItem m_undo=new JMenuItem("실행취소");
        m_undo.setAccelerator(KeyStroke.getKeyStroke('Z', CTRL_MASK));
        JMenuItem m_redo=new JMenuItem("다시실행");
        m_redo.setAccelerator(KeyStroke.getKeyStroke('Y', CTRL_MASK));
        JMenuItem m_cut=new JMenuItem("잘라내기");
        m_cut.setAccelerator(KeyStroke.getKeyStroke('X', CTRL_MASK));
        JMenuItem m_copy=new JMenuItem("복사");
        m_copy.setAccelerator(KeyStroke.getKeyStroke('C', CTRL_MASK));
        JMenuItem m_paste=new JMenuItem("붙혀넣기");
        m_paste.setAccelerator(KeyStroke.getKeyStroke('V', CTRL_MASK));
        JMenuItem m_del=new JMenuItem("삭제");
        m_del.setAccelerator(KeyStroke.getKeyStroke('D', CTRL_MASK));
        JMenuItem m_search=new JMenuItem("찾기");
        
        JMenuItem m_select_all=new JMenuItem("모두선택");
        m_select_all.setAccelerator(KeyStroke.getKeyStroke('A', CTRL_MASK));
        JMenuItem m_day_time=new JMenuItem("시간/날짜");
        
        
        m_edit.add(m_undo);
        m_edit.add(m_redo);
        m_edit.add(m_cut);
        m_edit.add(m_copy);
        m_edit.addSeparator();
        m_edit.add(m_paste);
        m_edit.add(m_del);
        m_edit.addSeparator();
        m_edit.add(m_search);
        m_edit.add(m_select_all);
        m_edit.add(m_day_time);
        
        // 색상
        JMenu m_color=new JMenu("색상");
        
        JMenuItem m_item1=new JMenuItem("빨강");
        JMenuItem m_item2=new JMenuItem("파랑");
        JMenuItem m_item3=new JMenuItem("노랑");
        
        m_color.add(m_item1);
        m_color.add(m_item2);
        m_color.add(m_item3);
        
        
 /////////////메뉴바/////////////       
        JMenuBar mb=new JMenuBar();
        mb.add(m_file);
        mb.add(m_edit);
        mb.add(m_color);
        setJMenuBar(mb);
        
        ta=new JTextArea();
        // tp = new JTextPane();
//        JScrollPane scroll_pain=new JScrollPane(ta);
        JScrollPane scroll_pain=new JScrollPane();
        scroll_pain.setViewportView(ta);
        

        JPopupMenu pop_menu=new JPopupMenu();
        JMenuItem pop_m_copy=new JMenuItem("복사");
        pop_m_copy.setAccelerator(KeyStroke.getKeyStroke('C', CTRL_MASK));
        JMenuItem pop_m_cut=new JMenuItem("잘라내기");
        pop_m_cut.setAccelerator(KeyStroke.getKeyStroke('X', CTRL_MASK));
        JMenuItem pop_m_paste=new JMenuItem("붙혀넣기");
        pop_m_paste.setAccelerator(KeyStroke.getKeyStroke('P', CTRL_MASK));
        pop_menu.add(pop_m_copy);
        pop_menu.add(pop_m_cut);
        pop_menu.add(pop_m_paste);
        ta.add(pop_menu);

        //add(scroll_pain);
        add(ta);


        //새파일
        m_new_note.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                ta.setText(null);
                ta.requestFocus();
                setTitle("메모장");
                setBackground(Color.YELLOW); // 시안
                ta.cut();
            }
        });
        
        //열기 (선택된 파일을 읽어들이기. fileread)
        m_open.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc=new JFileChooser();
                if(fc.showOpenDialog(Memo.this)==JFileChooser.CANCEL_OPTION)
                    return; //자신을 불러주는 부모창을 인수로함. Memo자기자신.
                f=fc.getSelectedFile(); //선택된 파일 
                setTitle(f.getName()); //선택된 파일의 이름 가져오기
                fileRead(f); //취소를 눌렀을경우 선택된 파일이 없기때문에 파일 읽어들이는 fileRead오류남
            }
        });
 
        //저장 (기존파일 없음=제목없음이면 새 창을 띄우기)
        m_save.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                if(getTitle().equals("제목없음")) { 
//                    JFileChooser fc=new JFileChooser();
//                    if(fc.showSaveDialog(Memo.this)==JFileChooser.CANCEL_OPTION)
//                        return;
//                    f=fc.getSelectedFile();
//                    fileSave(f);
//                    setTitle(f.getName());
                    m_save_as.doClick(); //mSaveAs가 다시 실행되는 것과 같음.
                }else {    //기존파일 있음
                    fileSave(f);
                }
            }
        });
 


        
        //새이름 저장
        m_save_as.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc=new JFileChooser();
                if(fc.showSaveDialog(Memo.this)==JFileChooser.CANCEL_OPTION)
                    return;
                f=fc.getSelectedFile();
//                System.out.println("파일 저장: "+f);
                fileSave(f);
                setTitle(f.getName());
            }
        });
        
        //끝내기
        m_exit.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        ////////Undo, Redo////////////
        ta.getDocument().addUndoableEditListener(new UndoableEditListener() {
            
            public void undoableEditHappend(UndoableEditEvent e) {
                undo_managet.addEdit(e.getEdit());
            }
        });
        
        
        pop_m_copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ta.copy();
            }
        });
        
        
        pop_m_cut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ta.cut();
            }
        });


        pop_m_paste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ta.paste();
            }
        });
    

        /* 
        ta.addMouseListener(new MouseListener(){
            // public void mousePressed(MouseEvent e){
                
                //         //pop_menu.show();
                // }
                
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    // TODO Auto-generated method stub
                    
                }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                // TODO Auto-generated method stub
                if(e.getButton()==MS_BUTTOM_RIGHT)
                pop_menu.show(ta, e.getX(), e.getY());
                
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                // TODO Auto-generated method stub
                
            }
        });
        */
        
        final int MS_BUTTOM_LEFT =1;
        final int MS_SCROLL_PUSH =2;
        final int MS_BUTTOM_RIGHT =3;

        ta.addMouseListener(new MouseAdapter(){
            public void mousePressed(java.awt.event.MouseEvent e) {
                // TODO Auto-generated method stub
                if(e.getButton()==MS_BUTTOM_RIGHT)
                pop_menu.show(ta, e.getX(), e.getY());
                
            }

        });


           

        setSize(500,400);
        setVisible(true);
        
    }
 
    public static void main(String[] args) {
        new Memo("메모장");
    }
    
    //파일 읽기 메소드
    private void fileRead(File f) {
        try {
            FileReader fr=new FileReader(f);
            StringWriter sw=new StringWriter();
            while(true) {
                int ch=fr.read();
                if(ch==-1) break;
                sw.write(ch);
            }
            ta.setText(sw.toString());
            sw.close();
            fr.close();
        } catch (FileNotFoundException n) {
            n.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
 
    //파일 저장 메소드
    private void fileSave(File f) {
        try {
            PrintStream ps=new PrintStream(f);
            ps.println(ta.getText());
            ps.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
    }

    
}