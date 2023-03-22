using UnityEngine;

public class BallRandomPositioner : MonoBehaviour
{
    public float speed = -15f;
    public Transform[] startPositions;

    public void FixedUpdate() {
        transform.Rotate(Vector3.up * speed * Time.fixedDeltaTime);

        foreach (Transform startPosition in startPositions)
        {
            startPosition.rotation = Quaternion.LookRotation(startPosition.position - transform.position, startPosition.up);
        }
    }
}
