using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class BalanceManager : MonoBehaviour
{

    public int balance;

    public Text balanceText,betText,paidText;

    public static BalanceManager instance;

    public static BalanceManager getInstance()
    {
        return instance;
    }

    void Start()
    {
        PlayerPrefs.SetInt("balance",100000);
        instance = this;
    }

    void Update()
    {
        balance = PlayerPrefs.GetInt("balance");
        balanceText.text = PlayerPrefs.GetInt("balance").ToString();
    }
}
