package com.fexmechine.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.fexmechine.control.Control;
import com.fexmechine.control.Controlissue;
import com.fexmechine.model.FexType;
import com.fexmechine.model.IssueType;
import com.fexmechine.speakUtil.Version;

import com.iflytek.cloud.speech.RecognizerListener;
import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.Setting;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechEvent;
import com.iflytek.cloud.speech.SpeechRecognizer;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.SynthesizerListener;

import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class Fexmechineframe2 extends JFrame {
	/*****************************************����ר��*******************************************/
	/*
	 * �����ϳ�
	 * 
	 * */
	// ȫ�ֱ�������
		private String mText = ""; // Ҫ���������ϳɵ����֣�����

		// �����ϳɶ���
		private SpeechSynthesizer mTts;
		//��ǰҪ��ʾ���ı�
		private String mCurText = "";
		//�����ı���ִ�ж���
		private TextRunnable mTextRunnable = new TextRunnable();
		
		private Map<String, String[]> mVoiceMap = new LinkedHashMap<String, String[]>();
		private Map<String, String> mParamMap1 = new HashMap<String, String>();
		
		private static class DefaultValue1{
			public static final String ENG_TYPE = SpeechConstant.TYPE_CLOUD;
			public static final String VOICE = "С��";
			public static final String BG_SOUND = "0";
			public static final String SPEED = "50";
			public static final String PITCH = "50";
			public static final String VOLUME = "50";
			public static final String SAVE = "0";
		}
		private void initParamMap1(){
			this.mParamMap1.put( SpeechConstant.ENGINE_TYPE, DefaultValue1.ENG_TYPE );
			this.mParamMap1.put( SpeechConstant.VOICE_NAME, DefaultValue1.VOICE );
			this.mParamMap1.put( SpeechConstant.BACKGROUND_SOUND, DefaultValue1.BG_SOUND );
			this.mParamMap1.put( SpeechConstant.SPEED, DefaultValue1.SPEED );
			this.mParamMap1.put( SpeechConstant.PITCH, DefaultValue1.PITCH );
			this.mParamMap1.put( SpeechConstant.VOLUME, DefaultValue1.VOLUME );
			this.mParamMap1.put( SpeechConstant.TTS_AUDIO_PATH, null );
		}
		
    /*
     * 
     * ����ʶ��
     * 
     * */
		
		// ������д����
		private SpeechRecognizer mIat; // ������д���󣬾���һ�����������
		
		private Map<String, String> mParamMap2 = new HashMap<String, String>();
		
		private String answerstr = ""; // ȫ�ֱ����Ĵ� 
		private int switchRecord = 0; // ¼����ɿ��أ�ֻ��¼������˲��ܽ����ж�
		
		
		// Ĭ������
		private static class DefaultValue2{
			public static final String ENG_TYPE = SpeechConstant.TYPE_CLOUD;
			public static final String SPEECH_TIMEOUT = "60000";
			public static final String NET_TIMEOUT = "20000";
			public static final String LANGUAGE = "zh_cn";
			
			public static final String ACCENT = "mandarin";
			public static final String DOMAIN = "iat";
			public static final String VAD_BOS = "5000";
			public static final String VAD_EOS = "1800";
			
			public static final String RATE = "16000";
			public static final String NBEST = "1";
			public static final String WBEST = "1";
			public static final String PTT = "0";
			
			public static final String RESULT_TYPE = "plain";
			public static final String SAVE = "0";
		}
		
		private void initParamMap2(){
			this.mParamMap2.put( SpeechConstant.ENGINE_TYPE, DefaultValue2.ENG_TYPE );
			this.mParamMap2.put( SpeechConstant.SAMPLE_RATE, DefaultValue2.RATE );
			this.mParamMap2.put( SpeechConstant.NET_TIMEOUT, DefaultValue2.NET_TIMEOUT );
			this.mParamMap2.put( SpeechConstant.KEY_SPEECH_TIMEOUT, DefaultValue2.SPEECH_TIMEOUT );
			
			this.mParamMap2.put( SpeechConstant.LANGUAGE, DefaultValue2.LANGUAGE );
			this.mParamMap2.put( SpeechConstant.ACCENT, DefaultValue2.ACCENT );
			this.mParamMap2.put( SpeechConstant.DOMAIN, DefaultValue2.DOMAIN );
			this.mParamMap2.put( SpeechConstant.VAD_BOS, DefaultValue2.VAD_BOS );
			
			this.mParamMap2.put( SpeechConstant.VAD_EOS, DefaultValue2.VAD_EOS );
			this.mParamMap2.put( SpeechConstant.ASR_NBEST, DefaultValue2.NBEST );
			this.mParamMap2.put( SpeechConstant.ASR_WBEST, DefaultValue2.WBEST );
			this.mParamMap2.put( SpeechConstant.ASR_PTT, DefaultValue2.PTT );
			
			this.mParamMap2.put( SpeechConstant.RESULT_TYPE, DefaultValue2.RESULT_TYPE );
			this.mParamMap2.put( SpeechConstant.ASR_AUDIO_PATH, null );
		}
	

	
	/****************************************ҳ��ר��***********************************************/
	
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JButton button_13;//���ʹ��水ť
	private Control control = new Control();
	private Controlissue controlissue = new Controlissue();
	
	private JLabel fexnumber_show;
	private JTextArea answers_frm;
	private JTextArea question_frm;
	private JLabel response_frm;
	
	private JLabel voice_state; // ��������״̬
	
	private FexType fextype;
	
	//���������
	private String password = "";
	private int count = 3; // ����������
	private int right_count = 0; // ��Դ�����һ��Ҫ�������3����Ŀ
	
	/**
	 * Create the frame.
	 * @param string 
	 */
	public Fexmechineframe2(FexType new_fextype) {
		// �������ã��ر���Ҫ��
		StringBuffer param = new StringBuffer();
		param.append( "appid=" + Version.getAppid() );
		SpeechUtility.createUtility( param.toString() );
		
		//ҳ����
		fextype = new_fextype;
		setTitle("�����2");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 756, 448);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button = new JButton("1");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectAnswer(0);
			}
		});
		button.setBounds(42, 157, 48, 27);
		contentPane.add(button);
		
		JButton button_1 = new JButton("2");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectAnswer(1);
			}
		});
		button_1.setBounds(100, 157, 48, 27);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("3");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectAnswer(2);
			}
		});
		button_2.setBounds(161, 157, 48, 27);
		contentPane.add(button_2);
		
		JButton button_3 = new JButton("4");
		button_3.setBounds(42, 194, 48, 27);
		contentPane.add(button_3);
		
		JButton button_4 = new JButton("5");
		button_4.setBounds(100, 194, 48, 27);
		contentPane.add(button_4);
		
		JButton button_5 = new JButton("6");
		button_5.setBounds(161, 194, 48, 27);
		contentPane.add(button_5);
		
		JButton button_6 = new JButton("7");
		button_6.setBounds(42, 237, 48, 27);
		contentPane.add(button_6);
		
		JButton button_7 = new JButton("8");
		button_7.setBounds(100, 237, 48, 27);
		contentPane.add(button_7);
		
		JButton button_8 = new JButton("9");
		button_8.setBounds(161, 237, 48, 27);
		contentPane.add(button_8);
		
		JButton button_9 = new JButton("*");
		button_9.setBounds(42, 280, 48, 27);
		contentPane.add(button_9);
		
		JButton button_10 = new JButton("0");
		button_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//��һ������
				fillQA();
			}
		});
		button_10.setBounds(100, 280, 48, 27);
		contentPane.add(button_10);
		
		JButton button_11 = new JButton("#");
		button_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ��������Ƿ���ȷ��
				checkPassword();
			}
		});
		button_11.setBounds(161, 280, 48, 27);
		contentPane.add(button_11);
		
		question_frm = new JTextArea();
		question_frm.setLineWrap(true);
		question_frm.setEditable(false);
		question_frm.setBounds(249, 196, 174, 54);
		contentPane.add(question_frm);
		
		JButton button_12 = new JButton("\u9ED1\u540D\u5355");
		button_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BlackRecieveList blacklist = new BlackRecieveList(2);
				blacklist.setVisible(true);
			}
		});
		button_12.setBounds(603, 111, 93, 36);
		contentPane.add(button_12);
		
		JButton btnNewButton = new JButton("\u767D\u540D\u5355");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WhiteRecieveList whitelist = new WhiteRecieveList(2);
				whitelist.setVisible(true);
			}
		});
		btnNewButton.setBounds(603, 171, 93, 36);
		contentPane.add(btnNewButton);
		
		// ���ʹ���,��frm2�в���������,�ѷ���
		JButton button_13 = new JButton("\u4F20\u771F/\u5F00\u59CB");
		button_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 String sendfrom = textField.getText();// ��ȡ���ͷ������
				 String sendto = textField_1.getText();// ��ȡ���շ������
				 
				 fextype = new FexType(2,sendfrom,sendto);
				 try {
					control.Judgement(fextype);
					//
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_13.setBounds(525, 270, 110, 104);
		contentPane.add(button_13);
		
		JButton button_14 = new JButton("\u505C\u6B62");
		button_14.setBounds(453, 270, 62, 62);
		contentPane.add(button_14);
		
		answers_frm = new JTextArea();
		answers_frm.setBounds(249, 260, 174, 50);
		contentPane.add(answers_frm);
		
		JLabel lblNewLabel = new JLabel("\u6765\u7535\u663E\u793A");
		lblNewLabel.setBounds(249, 127, 79, 19);
		contentPane.add(lblNewLabel);
		
		fexnumber_show = new JLabel("\u53F7\u7801\u663E\u793A");
		fexnumber_show.setBounds(249, 150, 174, 36);
		contentPane.add(fexnumber_show);
		
		JButton btnNewButton_1 = new JButton("\u590D\u5370");
		btnNewButton_1.setBounds(645, 270, 62, 62);
		contentPane.add(btnNewButton_1);
		
		JLabel label_1 = new JLabel("\u672C\u673A\u53F7\u7801\uFF1A");
		label_1.setBounds(42, 32, 77, 19);
		contentPane.add(label_1);
		
		textField = new JTextField();
		textField.setBounds(42, 61, 137, 23);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel label_2 = new JLabel("\u63A5\u6536\u65B9\u53F7\u7801\uFF1A");
		label_2.setBounds(42, 94, 97, 19);
		contentPane.add(label_2);
		
		textField_1 = new JTextField();
		textField_1.setBounds(42, 119, 137, 23);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		response_frm = new JLabel();
		response_frm.setBounds(249, 320, 174, 27);
		contentPane.add(response_frm);
		
		JButton button_15 = new JButton("\u95EE\u9898\u7BA1\u7406");
		button_15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				QuestionManager questionmanager= new QuestionManager();
				questionmanager.setVisible(true);
			}
		});
		button_15.setBounds(486, 111, 93, 36);
		contentPane.add(button_15);
		
		voice_state = new JLabel("\u8BED\u97F3\u64AD\u653E\u4E2D...");
		voice_state.setBounds(249, 83, 174, 34);
		contentPane.add(voice_state);
		
		JButton record_voice_btn = new JButton("\u7B54\u6848\u5F55\u97F3");
		record_voice_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				recordAnswer();
			}
		});
		record_voice_btn.setBounds(486, 171, 93, 36);
		contentPane.add(record_voice_btn);
		
		JButton updatePas_btn = new JButton("\u4FEE\u6539\u5BC6\u7801");
		updatePas_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Passwordframe pframe = new Passwordframe();
				pframe.setVisible(true);
			}
		});
		updatePas_btn.setBounds(486, 54, 93, 36);
		contentPane.add(updatePas_btn);
		
	
		
		// ��ʼ���ϳɶ���
		mTts = SpeechSynthesizer.createSynthesizer();
		// ��ʼ����д����
		mIat= SpeechRecognizer.createRecognizer();
		
		
		// ��ʼ�������ϳ�
		initParamMap1();
		initVoiceMap();
		
		// ��ʼ������ʶ��
		initParamMap2();
		
		// ִ�к���
		myEvent();
		
	}
	
	protected void checkPassword() {
		// TODO Auto-generated method stub
		String getpassword = answers_frm.getText();
		if(password.equals(getpassword)){
			response_frm.setText("������ȷ�����մ��棡");
			mText = response_frm.getText();
			speakEvent(); // ��������������������������
			// ��ӵ�������
			try {
				
				control.addW(fextype);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			response_frm.setText("�������");
			mText = response_frm.getText();
			speakEvent(); // ��������������������������
		}
	}

	private void myEvent() {
		fextype.setId(2);
		// �任
		fextype.exchange();
		
		fexnumber_show.setText(fextype.getRecieveNumber());
		int flag = control.Judgement(fextype);
		if(flag == 2){
			response_frm.setText("�ú���Ϊ���������룬�ܾ����մ��棡");
			mText = response_frm.getText();
			speakEvent(); // ��������������������������
		}else if(flag == 1){
			System.out.println("412213421");
			response_frm.setText("�ú���Ϊ���������룬���մ��棡");
			mText = response_frm.getText();
			speakEvent();
		}else{
			controlissue.createIssue();//��ȡ��������
			//�ȵȴ���������
			WaitPassword();
		}

	}
	
	// �ȴ��û������������ѡ�����
	private void WaitPassword(){
		
		password = control.getPassword();
		System.out.println(password);
		
		String str1 = "����������Ȼ��#�ż�ȷ�ϻ�������0�ż������ʴ�׶�";
		mText = str1;
		speakEvent();
		question_frm.setText(str1);
	}
	
	// չʾ������ѡ��
	private void fillQA(){
		answers_frm.setText("");
		
		IssueType iy = controlissue.getNextIssue();// ��ȡ��һ������
		// ��ȡ���������������
		question_frm.setText(iy.getQuestion());
		mText = question_frm.getText();
		speakEvent();
		
		Vector ansVector = iy.getAnswer();
		if(ansVector.get(0)!= null ){
			String ans = "1."+ansVector.get(0)+" 2."+ansVector.get(1)+" 3."+ansVector.get(2)+"\n";
			answers_frm.setText(ans);
		}
		response_frm.setText("���������"+count+"��");
	}
	
	// ѡ���
	protected void selectAnswer(int index) {
		int flag = controlissue.selectAns(index);
		if(flag == 1){
			right_count++;
			if(right_count >= 3){
				try {
					control.addW(fextype);
					response_frm.setText("����ȫ��ͨ�������մ��棡");
					mText = response_frm.getText();
					speakEvent();
					question_frm.setText("");
					answers_frm.setText("");
					count = 3;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				fillQA();
			}		
		}else{
			count--; // ������һ��
			right_count = 0; // ���¼���
			if(count < 0){
				try {
					control.addB(fextype);
					count = 3;
					response_frm.setText("����ʧ�ܣ������������");
					mText = response_frm.getText();
					speakEvent();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				fillQA();
			}
		}
	}
	
	// ������¼��
	public void recordAnswer(){
		settingRecord();
		voice_state.setText("¼�ƴ���...");
		if (!mIat.isListening())
			mIat.startListening(recognizerListener); // ��ʼ¼�����Ұ�recognizerListener����
		else
			mIat.stopListening();
		
	}
	
	
	
	/*****************************************����ר��*******************************************/
	/*
	 * ����ʶ��
	 * 
	 * */

	private RecognizerListener recognizerListener = new RecognizerListener() {

		@Override
		public void onBeginOfSpeech() {
		}

		@Override
		public void onEndOfSpeech() {
			System.out.println("onEndOfSpeech enter");
			System.out.println(answerstr);
			// ��¼������������ж�����ˣ�������
			
		}

		/**
		 * ��ȡ��д���. ��ȡRecognizerResult���͵�ʶ���������Խ�������ۼӣ���ʾ��Area��
		 */
		@Override
		public void onResult(RecognizerResult results, boolean islast) {
			voice_state.setText("¼�ƴ���...");
			String text = results.getResultString();
			answers_frm.append(text);
			
			answerstr = answerstr + text;	// ��ȡ������
			System.out.println(answerstr);
			// ��ȡ��֮������ڴ˴������ж�
			
			if(islast){
				voice_state.setText("����������...");
				int flag = controlissue.checkVoiceAns(answerstr);
				answerstr = "";
				if(flag == 1){
					right_count++;
					if(right_count >= 3){
						try {
							control.addW(fextype);
							response_frm.setText("����ȫ��ͨ�������մ��棡");
							mText = response_frm.getText();
							speakEvent();
							question_frm.setText("");
							answers_frm.setText("");
							count = 3;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						fillQA();
					}		
				}else{
					count--; // ������һ��
					right_count = 0; // ���¼���
					if(count < 0){
						try {
							control.addB(fextype);
							count = 3;
							response_frm.setText("����ʧ�ܣ������������");
							mText = response_frm.getText();
							speakEvent();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						fillQA();
					}
				}
			}
		}

		@Override
		public void onVolumeChanged(int volume) {
			voice_state.setText("¼�ƴ���...");
//			System.out.println("onVolumeChanged enter");
////			DebugLog.Log( "onVolumeChanged enter" );
//			if (volume == 0)
//				volume = 1;
//			else if (volume >= 6)
//				volume = 6;
		}

		@Override
		public void onError(SpeechError error) {
		}

		@Override
		public void onEvent(int eventType, int arg1, int agr2, String msg) {
		}
	};
	
	
	void settingRecord(){ // ��ʼ¼��֮����е�һ��С���ã������ÿ���ѡ�еĶ�������������������
		final String engType = this.mParamMap2.get(SpeechConstant.ENGINE_TYPE);
		
		for( Entry<String, String> entry : this.mParamMap2.entrySet() ){
			mIat.setParameter( entry.getKey(), entry.getValue() );
		}
	}
	
    /*
     * 
     * �����ϳ�
     * 
     * */
	// ����������ʾ
		private void speakEvent(){
			final String engType = this.mParamMap1.get(SpeechConstant.ENGINE_TYPE);
			String voiceName = null; 
			
			for( Entry<String, String> entry : this.mParamMap1.entrySet() ){
				String value = entry.getValue();
				if( SpeechConstant.VOICE_NAME.equals(entry.getKey()) ){
					String[] names = this.mVoiceMap.get( entry.getValue() );
					System.out.println(names);
					voiceName = value = SpeechConstant.TYPE_CLOUD.equals(engType) ? names[0] : names[1]; 
				}	
				mTts.setParameter( entry.getKey(), value );
			}
			
			//���úϳ���Ƶ���¼�������Ҫʱ���������ô˲���
			mTts.setParameter( SpeechConstant.TTS_BUFFER_EVENT, "1" );
			mTts.startSpeaking( mText, mSynListener );
		}
		
		
		private SynthesizerListener mSynListener = new SynthesizerListener() {

			@Override
			public void onSpeakBegin() {
			}

			@Override
			public void onBufferProgress(int progress, int beginPos, int endPos,
					String info) {
			}

			@Override
			public void onSpeakPaused() {

			}

			@Override
			public void onSpeakResumed() {

			}

			@Override
			public void onSpeakProgress(int progress, int beginPos, int endPos) {

				updateText( mText.substring( beginPos, endPos+1 ) );
		
			}

			@Override
			public void onCompleted(SpeechError error) {
				
				String text = mText;
				if (null != error){
					text = error.getErrorDescription(true);
				}
				voice_state.setText("");
				updateText( text );
				
			}


			@Override
			public void onEvent(int eventType, int arg1, int arg2, int arg3, Object obj1, Object obj2) {
				if( SpeechEvent.EVENT_TTS_BUFFER == eventType ){
					ArrayList<?> bufs = null;
					if( obj1 instanceof ArrayList<?> ){
						bufs = (ArrayList<?>) obj1;
					}else{
					}//end of if-else instance of ArrayList
					
					if( null != bufs ){
						for( final Object obj : bufs ){
							if( obj instanceof byte[] ){
								final byte[] buf = (byte[]) obj;
								
							}else{
						
							}
						}//end of for
					}//end of if bufs not null
				}//end of if tts buffer event
				//���´������ڵ��ԣ��������������Խ�sid�ṩ��Ѷ�ɿ����ߣ��������ⶨλ�Ų�
				/*else if(SpeechEvent.EVENT_SESSION_ID == eventType) {
					DebugLog.Log("sid=="+(String)obj2);
				}*/
			}
		};
		
		// ��������Ҫ�ã�����ɾ��
		private class TextRunnable implements Runnable{
			@Override
			public void run() {

			}	
		}
		private void updateText( final String text ){
			this.mCurText = text;
			SwingUtilities.invokeLater( mTextRunnable );
		}
		void initVoiceMap(){
			mVoiceMap.clear();
			String[] names = null;	
			names = new String[2];
			names[0] = names[1] = "xiaoyan";
			this.mVoiceMap.put( "С��", names );	//С��		
		}
}
