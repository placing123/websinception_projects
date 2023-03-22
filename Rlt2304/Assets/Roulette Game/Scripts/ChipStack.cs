using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using System;

public class ChipStack : MonoBehaviour {

    private Vector3 intiialPosition;

    int value = 0;

     List<GameObject> chips;

    static int NUM_CHIPS = 6;
    static int[] CHIP_VALUES = new int[] { 1, 5, 10, 25, 50, 100 };
    static string[] CHIP_PREFAB_NAMES = new string[] { "bluechip", "greenchip", "orangechip", "redchip", "blueskychip", "pinkchip" };

    bool moving;
    Vector3 targetPosition;
    float moveVelocity = 0f;
    float maxVelocity = 1.5f;

    void Start()
    {
        intiialPosition = transform.position;
    }

    public void Add(int value)
    {
        SetValue(this.value + value);
    }

    public void Remove(int value)
    {
        SetValue(this.value - value);
    }

    public void Clear()
    {
        value = 0;

        if (chips != null)
        {
            foreach (GameObject chip in chips)
            {
                Destroy(chip);
            }
        }

        chips = null;
    }

    public int GetValue()
    {
        return value;
    }

    public void SetValue(int value)
    {
        //BalanceManager.getInstance().balance += this.value;
        BalanceManager.instance.balance += this.value;
        //Debug.LogError("total win : " + value);
        //Debug.LogError("balance : " + BalanceManager.instance.balance);
        PlayerPrefs.SetInt("balance", BalanceManager.instance.balance);
        Clear();

        if (value <= 0)
        {
            return;
        }

        this.value = value;
        //BalanceManager.getInstance().balance -= this.value;
        BalanceManager.instance.balance -= this.value;
        int total_bet = int.Parse(BalanceManager.instance.betText.text) + this.value;
        BalanceManager.instance.betText.text = total_bet.ToString();
        //Debug.LogError("total win : " + value);
        //Debug.LogError("balance : " + BalanceManager.instance.balance);
        PlayerPrefs.SetInt("balance", BalanceManager.instance.balance);
        chips = new List<GameObject>();

        int currentChipIndex = NUM_CHIPS - 1;

        while (value > 0)
        {
            int nextValue = value - CHIP_VALUES[currentChipIndex];

            if (nextValue < 0)
            {
                currentChipIndex--;
                if (currentChipIndex < 0)
                {
                    throw new Exception("Impossible value");
                }
                continue;
            }

            value = nextValue;

            GameObject newChip = Instantiate(Resources.Load<GameObject>(CHIP_PREFAB_NAMES[currentChipIndex]));
            newChip.transform.parent = gameObject.transform;
            newChip.transform.localPosition = new Vector3(0, newChip.GetComponent<Renderer>().bounds.size.y * (chips.Count + 1), 0);
            newChip.layer = 10;

            chips.Add(newChip);
        }
    }

    public int Win(int multiplier)
    {
        int winAmount = value * multiplier;

        //BalanceManager.getInstance().balance += winAmount - value;
        BalanceManager.instance.balance += winAmount - value;
        //Debug.LogError("total win : " + value);
        //Debug.LogError("balance : " + BalanceManager.instance.balance);
        PlayerPrefs.SetInt("balance", BalanceManager.instance.balance);

        SetValue(winAmount);

        if (winAmount > 0)
        {
            Invoke("CollectChips", 2f);
        }

        return winAmount;
    }

    public void CollectChips()
    {
        moveVelocity = 0;
        moving = true;

        targetPosition = GameObject.Find("winningPosition").transform.position;

        AudioManager.getInstance().Play("collect", 1.0f);
    }

    void Update()
    {
        if (moving)
        {
            moveVelocity += 0.03f;
            moveVelocity = Mathf.Min(moveVelocity, maxVelocity);
            transform.position = Vector3.MoveTowards(transform.position, targetPosition, moveVelocity);

            if (Vector3.Distance(targetPosition, transform.position) < 1f)
            {
                moving = false;
                transform.position = intiialPosition;
                Clear();
            }
        }
    }
}
