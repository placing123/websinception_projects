using UnityEngine;
using UnityEngine.UI;
using System.Collections;

public class AmericanWheel : MonoBehaviour
{
    public static AmericanWheel Instance;
    public Ball ball;

    private GameObject resultCheckerObject;

    public float speed = 0.3f;
    public float ballspeed = 30f;

    int[] numbers = new int[] { 14, 2, 0, 28, 9, 26, 30, 11,
        7, 20, 32, 17, 5, 22, 34, 15, 3, 24, 36, 13, 1, -1, 27, // < The "-1" here is a key for "00"
        10, 25, 29, 12, 8, 19, 31, 18, 6, 21, 33, 16, 4, 23, 35 };

    float[] startValues = new float[] { 13.0f, 12.0f, 14.0f, 16.0f, 11.0f, 12.5f };

    public bool isDummy = false;

    public Text resultText;
    private void Awake()
    {
        if (Instance == null)
        {
            Instance = this;
        }
    }
    void Start()
    {
       /* for (int i = 0; i < 38; i++)
        {
            resultCheckerObject = Instantiate(Resources.Load<GameObject>("resultchecker"));
            resultCheckerObject.transform.RotateAround(transform.position, Vector3.up, (i - 0.4f) * 360 / 38);

            resultCheckerObject.transform.parent = transform;

            resultCheckerObject.GetComponent<ResultDetector>().result = numbers[i];
        }*/
    }
   public float[] ballFirstPos;
    public GameObject mainBall;
    public void Spin()
    {
        mainBall.transform.rotation =Quaternion.Euler(0, ballFirstPos[Random.Range(0, ballFirstPos.Length)], 0);
        ball.GetComponent<Rigidbody>().isKinematic = false;
        //AdsManager.instance.ShowThisSceneAds(1);
        CameraManager.getInstance().Spin();

        ResultManager.spinning = true;
        ResultManager.betsEnabled = false;
        ResultManager.ClearResult();

        resultText.text = "";

        int animId = Random.Range(0, 6);
        //ball.GetComponent<Animator>().enabled = true;
        AudioManager.getInstance().spin_sound.Play();
        //ball.Fire(startValues[animId]);
        //ball.Fire(30f);
        //  ball.Fire(ballspeed);
        ball.GetComponent<Animator>().enabled = true;
        ball.gameObject.GetComponent<Animator>().SetTrigger("move");
    }

    void FixedUpdate()
    {
        transform.Rotate(Vector3.up * speed);
    }
}