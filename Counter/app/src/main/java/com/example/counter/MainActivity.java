package com.example.counter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEditTextFirst, mEditTextSec;
    private TextView mTextView;

    private Button one, two, three, four, five, six, seven, eight, nine, zero;
    private Button point, percent;
    private Button plus, minus, divide, multiply;
    private Button clean, reduction;
    private Button equal;

    private BigDecimal n1, n2;//第一、第二个操作数
    private boolean isFirstNum = true;//是否是第一个操作数？默认是
    boolean calculated = false;//是否有计算过结果?默认没有计算过
    private BigDecimal result;//2个数据计算结果
    private String StrRes;//BigDecimal变成String的计算结果；
    private String opt = "+";//操作符


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditTextFirst = (EditText) findViewById(R.id.edit_text_first);
        mEditTextSec = (EditText) findViewById(R.id.edit_text_second);
        mTextView = (TextView) findViewById(R.id.textView);

        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);
        five = (Button) findViewById(R.id.five);
        six = (Button) findViewById(R.id.six);
        seven = (Button) findViewById(R.id.seven);
        eight = (Button) findViewById(R.id.eight);
        nine = (Button) findViewById(R.id.nine);
        zero = (Button) findViewById(R.id.zero);

        point = (Button) findViewById(R.id.point);
        percent = (Button) findViewById(R.id.percent);

        plus = (Button) findViewById(R.id.plus);
        minus = (Button) findViewById(R.id.minus);
        multiply = (Button) findViewById(R.id.multiply);
        divide = (Button) findViewById(R.id.divide);

        clean = (Button) findViewById(R.id.clean);
        reduction = (Button) findViewById(R.id.reduction);

        equal = (Button) findViewById(R.id.equal);

        //button添加监听
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        point.setOnClickListener(this);
        zero.setOnClickListener(this);

        clean.setOnClickListener(this);
        reduction.setOnClickListener(this);

        equal.setOnClickListener(this);
        percent.setOnClickListener(this);
        divide.setOnClickListener(this);
        multiply.setOnClickListener(this);

        minus.setOnClickListener(this);

        plus.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        String buttonText = b.getText().toString();
        switch (v.getId()) {
            case R.id.zero:
            case R.id.one:
            case R.id.two:
            case R.id.three:
            case R.id.four:
            case R.id.five:
            case R.id.six:
            case R.id.seven:
            case R.id.eight:
            case R.id.nine:

                /**
                 * 如果是0，再次输入0时,仍为0，
                 *         输入非0时，显示非0的数；
                 *如果是非0，输入新的数后，为2个数拼接
                 * 如果是第一个数据，mEditTextFirst显示出输入的数据;
                 * 否则mEditTextSec显示出输入的数据
                 */
                hasCalculated();
                if (isFirstNum) {
                    String str = mEditTextFirst.getText().toString();
                    if (str.equals("0")) {
                        if (buttonText.equals("0")) {
                            str = 0 + "";
                        } else {
                            str = buttonText;
                        }
                    } else {
                        str += buttonText;
                        str = determineNum(str);
                    }
                    mEditTextFirst.setText(str);
                    mTextView.setText(str);
                } else {
                    String str = mEditTextSec.getText().toString();
                    //str.substring(0,1)是操作符，str.substring(1)是操作数
                    opt = str.substring(0, 1);
                    String subStr = str.substring(1);
                    if (subStr.equals("0")) {
                        if (buttonText.equals("0")) {
                            str = opt + 0;
                        } else {
                            str = opt + buttonText;
                        }
                    } else {
                        str = opt + subStr + buttonText;
                        str = determineNum(str);
                    }
                    mEditTextSec.setText(str);
                }
                break;

            /**小数点
             * 判断是第几个操作数
             * 看该数据里是否包含小数点，若不包含mEditTextFirst显示出输入的数据;
             */
            case R.id.point:
                hasCalculated();
                if (isFirstNum) {
                    String str = mEditTextFirst.getText().toString();
                    if (str.equals("")) {
                        str = "0" + ".";
                    } else if (str.contains(".")) {
                        str = str + "";
                    } else {
                        str += ".";
                    }
                    mEditTextFirst.setText(str);
                    mTextView.setText(str);

                } else {
                    String str = mEditTextSec.getText().toString();
                    String subString = str.substring(1);
                    if (subString.equals("")) {
                        str = str.substring(0, 1) + "0" + ".";
                    } else if (subString.contains(".")) {
                        str = str + "";
                    } else {
                        str += ".";
                    }
                    mEditTextSec.setText(str);
                }
                break;

            /**百分号
             * 如果是0或0.或0.00后跟%都显示0
             * 如果是第一个数据，该数据乘以0.01，mEditTextFirst显示出输入的数据;
             * 如果是第第二个数据，该数据乘以0.01， mEditTextSec显示出输入的数据
             */
            case R.id.percent:
                hasCalculated();
                if (isFirstNum) {
                    String str = mEditTextFirst.getText().toString();
                    if (str.equals("")) {
                        //如果操作数，直接输入%，显示0
                        mEditTextFirst.setText("0");
                        mTextView.setText("0");
                    } else {
                        String countPercentResult = countPercent(str);
                        mEditTextFirst.setText(countPercentResult);
                        mTextView.setText(countPercentResult);
                    }
                } else {
                    String str = mEditTextSec.getText().toString();
                    if (str.length() == 1) {
                        mEditTextSec.setText(str);

                    } else if (str.length() > 1) {//含操作符号和操作数
                        String subStr = str.substring(1);
                        String countPercentResult = countPercent(subStr);
                        str = str.substring(0, 1) + countPercentResult;
                        mEditTextSec.setText(str);
                    }
                }
                break;

            /**
             * 加减乘除操作符
             */
            case R.id.plus:
            case R.id.minus:
            case R.id.multiply:
            case R.id.divide:
                hasCalculated();
                String str = buttonText;
                opt = str;
                mEditTextSec.setText(str);
                isFirstNum = false;
                break;

            /**
             * 减少最后一位数
             */
            case R.id.reduction:
                hasCalculated();
                if (isFirstNum) {
                    String firstStr = mEditTextFirst.getText().toString();
                    if (firstStr.length() != 1) {
                        mEditTextFirst.setText(firstStr.substring(0, firstStr.length() - 1));
                    } else {
                        mEditTextFirst.setText(0 + "");
                    }
                } else {
                    String secondStr = mEditTextSec.getText().toString();
                    if (secondStr.length() != 1) {
                        mEditTextSec.setText(secondStr.substring(0, secondStr.length() - 1));
                    } else {
                        mEditTextSec.setText(0 + "");
                        isFirstNum = true;
                    }
                }
                break;
            /**
             *清除所有内容
             */
            case R.id.clean:
                mEditTextFirst.setText(0 + "");
                mEditTextSec.setText(0 + "");
                mTextView.setText(0 + "");
                isFirstNum = true;
                break;

            /**
             *等于
             */
            case R.id.equal:
                calculated = true;
                n1 = strTransBigDecimal(mEditTextFirst.getText().toString());
                String string2 = mEditTextSec.getText().toString();
                if (string2.length() <= 1) {
                    result = n1; //如果没有第二位操作数则显示第一位操作数
                } else {
                    n2 = strTransBigDecimal(string2.substring(1, string2.length()));

                    switch (opt) {
                        case "+":
                            result = n1.add(n2);
                            returnResult();
                            break;

                        case "-":
                            result = n1.subtract(n2);
                            returnResult();
                            break;

                        case "x":
                            result = n1.multiply(n2);
                            returnResult();
                            break;

                        /**
                         * 先判断除数为0，则给出不能除以0的提示；
                         * 接着判断被除数为0，结果为0；
                         * 最后除数和被除数都不为0的情况
                         */
                        case "÷":
                            if (n2.doubleValue() == 0.0) {
                                mTextView.setText(R.string.cannot);
                                isFirstNum = true;
                            } else if (n1.doubleValue() == 0.0) {
                                mTextView.setText("0");
                                isFirstNum = true;
                            } else {
                                result = n1.divide(n2, 8, RoundingMode.HALF_UP);
                                returnResult();
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                }
            default:
                break;
        }
    }

    /**
     * 百分号计算结果
     */
    private String countPercent(String str) {
        BigDecimal bigDecimal = new BigDecimal(str);
        bigDecimal = bigDecimal.multiply(new BigDecimal("0.01"), new MathContext(9, RoundingMode.HALF_UP));
        str = bigDecimal.toString();
        return removeUnnecessaryContent(str);
    }

    /**
     * 判断操作数位数。
     * 如果str长度为16-20位 ，如果没有小数点显示15位，如果有小数点 显示20位。
     * 超过20位截断
     */
    private String determineNum(String str) {
        if (15 < str.length() && str.length() <= 20) {
            if (!str.contains(".")) {
                str = str.substring(0, 15);
            } else {
                str = str.substring(0);
            }
        }
        if (str.length() > 20) {
            str = str.substring(0, 20);
        }
        return str;
    }

    /**
     * 显示2个操作数执行后结果，并将isFirstNum置为true
     * 如果小数点后都为0，则直接显示整数
     */
    private void returnResult() {
        String strResult = result.toString();
        mTextView.setText(removeUnnecessaryContent(strResult));
        isFirstNum = true;
    }

    /**
     * 去除小数点后不必要的内容，""或最后一位是0
     */
    public String removeUnnecessaryContent(String str) {
        if (str.contains(".")) {
            String splitStr[] = str.split("\\.");
            String subSplitStr = splitStr[1];
            if (subSplitStr.equals("") || subSplitStr.equals("0")) {
                StrRes = splitStr[0];
                return StrRes;
            }

            while (subSplitStr.endsWith("0")) {
                subSplitStr = subSplitStr.substring(0, subSplitStr.length() - 1);//去掉最后一位
                if (subSplitStr.equals("0")) {
                    StrRes = splitStr[0];
                }

            }
            if (subSplitStr.equals("")) {
                StrRes = splitStr[0];
            } else {
                StrRes = splitStr[0] + "." + subSplitStr;
            }
            return StrRes;
        } else {
            return str;
        }
    }

    /**
     * String转为BigDecimal
     */
    private BigDecimal strTransBigDecimal(String s) {
        return new BigDecimal(s, new MathContext(9, RoundingMode.HALF_UP));
    }

    /**
     * 判断是否已经计算过结果，若计算过，所有输入显示框置为0
     */
    public void hasCalculated() {
        if (calculated == true) {
            mEditTextFirst.setText("0");
            mEditTextSec.setText("0");
            mTextView.setText("0");
            calculated = false;
            isFirstNum = true;
        }
    }
}


