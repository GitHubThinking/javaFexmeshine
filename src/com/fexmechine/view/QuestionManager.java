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
	
	// 全局变量设置
	private String mText = "请点击录制问题按钮进行问题录制";
	private int flag = 1; // 1为录制问题，2位录制答案
	
	/*
	 * 以下为语音合成设置
	 * */
	
	// 语音合成对象
	private SpeechSynthesizer mTts;
	//当前要显示的文本
	private String mCurText = "";
	//更新文本的执行对象
	private TextRunnable mTextRunnable = new TextRunnable();
	
	private Map<String, String[]> mVoiceMap = new LinkedHashMap<String, String[]>();
	private Map<String, String> mParamMap1 = new HashMap<String, String>();
	
	private static class DefaultValue1{
		public static final String ENG_TYPE = SpeechConstant.TYPE_CLOUD;
		public static final String VOICE = "小燕";
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
	 * 以下为语音识别设置！
	 * */
	
	// 语音听写对象
	private SpeechRecognizer mIat; // 语音听写对象，就是一个大类的作用
	
	private Map<String, String> mParamMap2 = new HashMap<String, String>();
	
	// 默认设置
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
		//必须要加上这个，否则就会出错！
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

		
		// 初始化合成对象
		mTts = SpeechSynthesizer.createSynthesizer();
		// 初始化听写对象
		mIat= SpeechRecognizer.createRecognizer();
		
		
		// 初始化语音合成
		initParamMap1();
		initVoiceMap();
		
		// 初始化语音识别
		initParamMap2();
		
		// 先播放语音提示！
		speakEvent();
		

	}
	
	/**
	 * 按钮监听器实现
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == question_btn) {
			// 设置录音
			state_label.setText("录制问题中...");
			questionArea.setText("");
			settingRecord(1);
			if (!mIat.isListening())
				mIat.startListening(recognizerListener); // 开始录音并且把recognizerListener传入
			else
				mIat.stopListening();
		}else if(e.getSource() == answer_btn ){
			state_label.setText("录制答案中...");
			answerArea.setText("");
			settingRecord(2);
			if (!mIat.isListening())
				mIat.startListening(recognizerListener); // 开始录音并且把recognizerListener传入
			else
				mIat.stopListening();
		}else if(e.getSource() == clear_btn){
			questionArea.setText("");
			answerArea.setText("");
		}else if(e.getSource() == add_btn){
			// 添加到数据题库
			IssueType itype = new IssueType();
			itype.setQuestion(questionArea.getText());
			itype.setAnswer(new Vector());
			itype.setTrue_ans(answerArea.getText());
			controlissue.addQue_Ans(itype);
			
			System.out.println("添加题目成功");
			questionArea.setText("");
			answerArea.setText("");
		}
	}
	
	/*
	 * 
	 * 语音合成的函数配置
	 * 
	 * */

	// 播放语音提示
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
		
		//启用合成音频流事件，不需要时，不用设置此参数
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
			//以下代码用于调试，如果出现问题可以将sid提供给讯飞开发者，用于问题定位排查
			/*else if(SpeechEvent.EVENT_SESSION_ID == eventType) {
				DebugLog.Log("sid=="+(String)obj2);
			}*/
		}
	};
	
	// 更新语音要用，不能删！
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
		this.mVoiceMap.put( "小燕", names );	//小燕		
	}
	
	
	
	/*
	 * 
	 * 语音识别函数设置
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
		 * 获取听写结果. 获取RecognizerResult类型的识别结果，并对结果进行累加，显示到Area里
		 */
		@Override
		public void onResult(RecognizerResult results, boolean islast) {
			String text = results.getResultString();
			if(flag == 1){
				state_label.setText("录制问题中...");
				questionArea.append(text);
				text = questionArea.getText();
			}else if(flag == 2){
				state_label.setText("录制答案中...");
				answerArea.append(text);
				text = answerArea.getText();
			}
			
			if(islast){
				state_label.setText("等待录音");
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
	
	
	void settingRecord(int i){ // 开始录音之后进行的一个小设置，将设置框中选中的东西保存起来！！！！
		flag = i;
		final String engType = this.mParamMap2.get(SpeechConstant.ENGINE_TYPE);
		
		for( Entry<String, String> entry : this.mParamMap2.entrySet() ){
			mIat.setParameter( entry.getKey(), entry.getValue() );
		}
	}
}
