using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class BetSpace : MonoBehaviour {

    GameObject chipStackGameObject;
    [HideInInspector]
    public ChipStack chipStack;

    public int[] winningNumbers = new int[] { 1 };

    public MeshRenderer[] betSpaceRender;
    public AudioSource hove_sfx,click_sound;
    private MeshRenderer mesh;

    public static int numLenght = 36; //Change this to change the amount of rewards

    void Start()
    {
        mesh = GetComponent<MeshRenderer>();

        if (mesh)
            mesh.enabled = false;
        

        chipStackGameObject = Instantiate(Resources.Load<GameObject>("chipstack"));

        chipStackGameObject.transform.position = transform.position;
        chipStackGameObject.transform.parent = transform.parent;

        chipStack = chipStackGameObject.GetComponent<ChipStack>();

        ResultManager.RegisterBetSpace(this);
    }

    void OnMouseOver()
    {
        
        ToolTipManager.getInstance().target = chipStack;
        
    }

    private void OnMouseEnter()
    {
        hove_sfx.Play();
        if (mesh)
            mesh.enabled = true;

        if (betSpaceRender.Length > 0)
        {
            foreach (MeshRenderer spaceRender in betSpaceRender)
            {
                spaceRender.enabled = true;
            }
        }
    }

    void OnMouseExit()
    {
        if (mesh)
            mesh.enabled = false;

        if (betSpaceRender.Length > 0)
        {
            foreach (MeshRenderer spaceRender in betSpaceRender)
            {
                spaceRender.enabled = false;
            }
        }
    }

	void OnMouseDown()
    {
        int selectedValue = ChipManager.GetSelectedValue();

        if (ResultManager.betsEnabled && /*BalanceManager.getInstance().balance*/ BalanceManager.instance.balance >= selectedValue)
        {
            click_sound.Play();
            AudioManager.getInstance().Play("chip", 1.0f);

            BetHistoryManager.getInstance().Add(chipStack, selectedValue);
            chipStack.Add(selectedValue);
        }
    }

   public int ResolveBet(int result)
    {
        int multiplier = numLenght / winningNumbers.Length;


        bool won = false;

        foreach (int num in winningNumbers)
        {
            if (num == result)
            {
                won = true;
                break;
            }
        }

        int winAmount = 0;

        if (won)
        {
            winAmount = chipStack.Win(multiplier);
        } else
        {
            chipStack.Clear();
        }

        return winAmount;
    }
}
