using UnityEngine;
using System.Collections;

public class Ball : MonoBehaviour {


    private BallRandomPositioner rPos;

    // Use this for initialization
    void Start () {
        rPos = GameObject.Find("RandomPositions").GetComponent<BallRandomPositioner>();
        GetComponent<Rigidbody>().isKinematic = true;
	}

    public void Fire(float speed)
    {
        //this.GetComponent<Animator>().enabled = false;
        if (!rPos)
            GameObject.Find("RandomPositions").GetComponent<BallRandomPositioner>();

        GetComponent<Rigidbody>().isKinematic = false;
       this.GetComponent<Rigidbody>().drag = 0.05f;
        GetComponent<Rigidbody>().velocity = Vector3.zero;
        GetComponent<Rigidbody>().angularVelocity = Vector3.zero;

        Transform t = rPos.startPositions[Random.Range(0, 4)];
        //Transform t = rPos.startPositions[Random.Range(0, 1)];

        transform.position = t.position;
        transform.rotation = t.rotation;
        //Debug.Log("position : " + transform.position);
        
        GetComponent<Rigidbody>().AddForce(transform.right * speed  ,ForceMode.Impulse);
       
    }

    

    void Update()
    {
        if (GetComponent<Rigidbody>().velocity.magnitude < 5)
        {
            AudioManager.getInstance().BallStopped();
            this.GetComponent<Rigidbody>().drag = 0.08f;
        }
        else
        {
          //  AudioManager.getInstance().BallRolling();
        }

    }
   /* private void OnCollisionEnter(Collision other)
    {
        
        if (other.collider.tag == "jump")
        {
            GetComponent<Rigidbody>().AddForce(new Vector3(0,3,0),ForceMode.Impulse);
            other.gameObject.SetActive(false);
        }
    }*/

    public void CloseAnim()
    {
        GetComponent<Animator>().enabled = false;
        AudioManager.getInstance().BallStopped();
    }
}
