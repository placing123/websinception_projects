using UnityEngine;
using UnityEngine.UI;
using System.Collections;

public class EuropeanWheel : MonoBehaviour
{

    public Ball ball;

    private GameObject resultCheckerObject;

    float speed = 0.3f;

    int[] numbers = new int[] { 0, 32, 15, 19, 4, 21, 2, 25, 17, 34, 6, 27, 13, 36, 11, 30, 8, 23, 10, 5, 24, 16, 33, 1, 20, 14, 31, 9, 22, 18, 29, 7, 28, 12, 35, 3, 26 };
    float[] startValues = new float[] { 13.0f, 12.0f, 14.0f, 16.0f, 11.0f, 12.5f };

    public Text resultText;

    void Start()
    {
        for (int i = 0; i < 37; i++)
        {
            resultCheckerObject = Instantiate(Resources.Load<GameObject>("resultchecker"));
            resultCheckerObject.transform.RotateAround(transform.position, Vector3.up, i * 360 / 37);
                                                                                                              
            resultCheckerObject.transform.parent = transform;

            resultCheckerObject.GetComponent<ResultDetector>().result = numbers[i];
        }
    }

    public void Spin()
    {
        CameraManager.getInstance().Spin();

        ResultManager.spinning = true;
        ResultManager.betsEnabled = false;
        ResultManager.ClearResult();

        resultText.text = "";

        int animId = Random.Range(0, 6);
        ball.Fire(startValues[animId]);
    }

    void FixedUpdate()
    {
        transform.Rotate(Vector3.up * speed);
    }
}
