using UnityEngine;
using System.Collections;

public class ResultDetector : MonoBehaviour {

    bool hitting;
    int hitTime;

    public int result;
   
    void OnTriggerEnter(Collider col)
    {
        if (col.gameObject.name == "ball")
        {
            hitting = true;
            Debug.Log("Dgfghfghfg");
        }
    }

    void OnTriggerExit(Collider col)
    {
        if (col.gameObject.name == "ball")
        {
            hitting = false;
            Debug.Log("aaaaaaaaaaaaaa");
        }
    }

    void Update()
    {
        if (hitting && ResultManager.spinning)
        {
         //   Debug.LogError("hit" + hitting);
            hitTime++;
        } else
        {
            hitTime = 0;
         //   Debug.LogError("hitttt" + hitting);
        }

        if (hitTime > 100)
        {
            ResultManager.SetResult(result);
            AmericanWheel.Instance.ball.GetComponent<Rigidbody>().isKinematic = true;
        }
    }
}
