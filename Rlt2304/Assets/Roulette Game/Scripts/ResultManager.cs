using UnityEngine;
using System.Collections;
using System;
using System.Collections.Generic;

public static class ResultManager {

    public static bool spinning = false;
    public static bool betsEnabled = true;

    public static int result;

    static List<BetSpace> betSpaces = new List<BetSpace>();
    

    public static void SetResult(int newResult)
    {
        if (spinning)
        {
            spinning = false;

            result = newResult;
            

            int totalWin = 0;

            foreach (BetSpace betSpace in betSpaces)
            {
                totalWin += betSpace.ResolveBet(result);
            }


            if (result != -1)
                GameObject.Find("high" + result.ToString()).GetComponent<MeshRenderer>().enabled = true;
            /*else
                GameObject.Find("high00").GetComponent<MeshRenderer>().enabled = true;*/


            //BalanceManager.getInstance().balance += totalWin;
            BalanceManager.instance.balance += totalWin;
            //Debug.LogError("total win : " + totalWin);
            //Debug.LogError("balance : " + BalanceManager.instance.balance);
            PlayerPrefs.SetInt("balance", BalanceManager.instance.balance);
            BetHistoryManager.getInstance().ClearHistory();

            GameObject.Find("WinSequence").GetComponent<WinSequence>().ShowResult(result, totalWin);

        }
    }

    public static void ClearResult()
    {
        GameObject previousResultHighlight = GameObject.Find("high" + result.ToString());
        if (previousResultHighlight != null) { 
        previousResultHighlight.GetComponent<MeshRenderer>().enabled = false;
        }
    }

    public static void RegisterBetSpace(BetSpace betSpace)
    {
        betSpaces.Add(betSpace);
    }
}