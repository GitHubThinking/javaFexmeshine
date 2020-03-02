package com.fexmechine.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.fexmechine.control.Controlissue;
import com.fexmechine.model.IssueType;
import com.fexmechine.speakUtil.Version;
import com.iflytek.cloud.speech.RecognizerListener;
import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.ResourceUtil;
import com.iflytek.cloud.speech.Setting;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechEvent;
import com.iflytek.cloud.speech.SpeechRecognizer;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.SynthesizerListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.JRadioButton;

public class QuestionManager extends JFrame implements ActionListener{

	private Controlissue controlissue = new Controlissue();
	
	private JPanel contentPane;

	private JLabel state_label;
	private JTextArea questionArea;
	private JTextArea answerArea;
	private JButton question_btn;
	private JButton answer_btn;
	private JButton clear_btn;
	private JButton add_btn;
	
	// ȫ�ֱ�������
	private String mText = "����¼�����ⰴť��������¼��";
	private int flag = 1; // 1Ϊ¼�����⣬2λ¼�ƴ�
	
	/*
	 * ����Ϊ�����ϳ�����
	 * */
	
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
	 * ����Ϊ����ʶ�����ã�
	 * */
	
	// ������д����
	private SpeechRecognizer mIat; // ������д���󣬾���һ�����������
	
	private Map<String, String> mParamMap2 = new HashMap<String, String>();
	
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
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuestionManager frame = new QuestionManager();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public QuestionManager() {
		//����Ҫ�������������ͻ����
		StringBuffer param = new StringBuffer();
		param.append( "appid=" + Version.getAppid() );

		SpeechUtility.createUtility( param.toString() );
		Setting.setShowLog(false);
		setBounds(100, 100, 325, 316);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		question_btn = new JButton("\u5F55\u5236\u95EE\u9898");
		question_btn.setBounds(190, 98, 93, 23);
		contentPane.add(question_btn);
		
		JLabel lblNewLabel = new JLabel("\u95EE\u9898");
		lblNewLabel.setBounds(38, 17, 54, 15);
		contentPane.add(lblNewLabel);
		
		questionArea = new JTextArea();
		questionArea.setBounds(38, 42, 123, 112);
		contentPane.add(questionArea);
		
		JLabel label = new JLabel("\u7B54\u6848");
		label.setBounds(38, 164, 54, 15);
		contentPane.add(label);
		
		answerArea = new JTextArea();
		answerArea.setBounds(38, 189, 123, 43);
		contentPane.add(answerArea);
		
		answer_btn = new JButton("\u5F55\u5236\u7B54\u6848");
		answer_btn.setBounds(190, 131, 93, 23);
		contentPane.add(answer_btn);
		
		clear_btn = new JButton("\u6E05\u7A7A");
		clear_btn.setBounds(190, 178, 93, 23);
		contentPane.add(clear_btn);
		
		state_label = new JLabel("\u7B49\u5F85\u5F55\u5236\u95EE\u9898");
		state_label.setBounds(187, 46, 96, 23);
		contentPane.add(state_label);
		
		add_btn = new JButton("\u6DFB\u52A0");
		add_btn.setBounds(190, 211, 93, 23);
		contentPane.add(add_btn);
		
		
		question_btn.addActionListener(this);
		answer_btn.addActionListener(this);
		clear_btn.addActionListener(this);
		add_btn.addActionListener(this);

		
		// ��ʼ���ϳɶ���
		mTts = SpeechSynthesizer.createSynthesizer();
		// ��ʼ����д����
		mIat= SpeechRecognizer.createRecognizer();
		
		
		// ��ʼ�������ϳ�
		initParamMap1();
		initVoiceMap();
		
		// ��ʼ������ʶ��
		initParamMap2();
		
		// �Ȳ���������ʾ��
		speakEvent();
		

	}
	
	/**
	 * ��ť������ʵ��
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == question_btn) {
			// ����¼��
			state_label.setText("¼��������...");
			questionArea.setText("");
			settingRecord(1);
			if (!mIat.isListening())
				mIat.startListening(recognizerListener); // ��ʼ¼�����Ұ�recognizerListener����
			else
				mIat.stopListening();
		}else if(e.getSource() == answer_btn ){
			state_label.setText("¼�ƴ���...");
			answerArea.setText("");
			settingRecord(2);
			if (!mIat.isListening())
				mIat.startListening(recognizerListener); // ��ʼ¼�����Ұ�recognizerListener����
			else
				mIat.stopListening();
		}else if(e.getSource() == clear_btn){
			questionArea.setText("");
			answerArea.setText("");
		}else if(e.getSource() == add_btn){
			// ��ӵ��������
			IssueType itype = new IssueType();
			itype.setQuestion(questionArea.getText());
			itype.setAnswer(new Vector());
			itype.setTrue_ans(answerArea.getText());
			controlissue.addQue_Ans(itype);
			
			System.out.println("�����Ŀ�ɹ�");
			questionArea.setText("");
			answerArea.setText("");
		}
	}
	
	/*
	 * 
	 * �����ϳɵĺ�������
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
	
	
	
	/*
	 * 
	 * ����ʶ��������
	 * 
	 * */
	
	private RecognizerListener recognizerListener = new RecognizerListener() {

		@Override
		public void onBeginOfSpeech() {
		}

		@Override
		public void onEndOfSpeech() {
			System.out.println("onEndOfSpeech enter");
//			DebugLog.Log( "onEndOfSpeech enter" );
		}

		/**
		 * ��ȡ��д���. ��ȡRecognizerResult���͵�ʶ���������Խ�������ۼӣ���ʾ��Area��
		 */
		@Override
		public void onResult(RecognizerResult results, boolean islast) {
			String text = results.getResultString();
			if(flag == 1){
				state_label.setText("¼��������...");
				questionArea.append(text);
				text = questionArea.getText();
			}else if(flag == 2){
				state_label.setText("¼�ƴ���...");
				answerArea.append(text);
				text = answerArea.getText();
			}
			
			if(islast){
				state_label.setText("�ȴ�¼��");
			}
		}

		@Override
		public void onVolumeChanged(int volume) {
			System.out.println("onVolumeChanged enter");
//			DebugLog.Log( "onVolumeChanged enter" );
			if (volume == 0)
				volume = 1;
			else if (volume >= 6)
				volume = 6;
		}

		@Override
		public void onError(SpeechError error) {
		}

		@Override
		public void onEvent(int eventType, int arg1, int agr2, String msg) {
		}
	};
	
	
	void settingRecord(int i){ // ��ʼ¼��֮����е�һ��С���ã������ÿ���ѡ�еĶ�������������������
		flag = i;
		final String engType = this.mParamMap2.get(SpeechConstant.ENGINE_TYPE);
		
		for( Entry<String, String> entry : this.mParamMap2.entrySet() ){
			mIat.setParameter( entry.getKey(), entry.getValue() );
		}
	}
}
