using UnityEngine;
using System.Collections;

public class CameraManager : MonoBehaviour {

    public GameObject desktopCameras;
    public GameObject mobileCameras;

    GameObject cameras;

    static CameraManager instance;

    // Use this for initialization
    void Start () {
        instance = this;

        /*if (DebugManager.getInstance().forceMobile || Application.platform == RuntimePlatform.Android || Application.platform == RuntimePlatform.IPhonePlayer)
        {*/
            desktopCameras.SetActive(false);
            mobileCameras.SetActive(true);

            cameras = mobileCameras;
        /*}
        else
        {
            desktopCameras.SetActive(true);
            //mobileCameras.SetActive(true);

            cameras = desktopCameras;
        }*/
	}

    public void Spin()
    {
        /*cameras.transform.GetChild(0).gameObject.SetActive(false);
        cameras.transform.GetChild(1).gameObject.SetActive(true);*/
    }

    public void Bet()
    {
        /*cameras.transform.GetChild(1).gameObject.SetActive(false);
        cameras.transform.GetChild(0).gameObject.SetActive(true);*/
    }

    public static CameraManager getInstance()
    {
        return instance;
    }
}
