using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
public class SplashScreen : MonoBehaviour
{
    void Start()
    {
        //PlayerPrefs.DeleteAll();
        StartCoroutine("Load");
        //  AdsManager.instance.HideBanner();
    }

    IEnumerator Load()
    {
        yield return new WaitForSeconds(1f);
        SceneManager.LoadScene("AmericanWheelScene");
    }
}
