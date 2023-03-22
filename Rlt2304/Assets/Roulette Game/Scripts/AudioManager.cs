using UnityEngine;
using UnityEngine.UI;
using System.Collections;
using System.Collections.Generic;
using System;

public class AudioManager : MonoBehaviour 
{
    public AudioSource spin_sound;
    static AudioManager instance;

    AudioSource audioSource;
    private bool rolling;

    // Use this for initialization
    void Start () {
        instance = this;

        audioSource = gameObject.GetComponent<AudioSource>();
    }

    public void PlayButtonSound(float volume)
    {
        Play("press_but", volume);
    }

    public void Play(string clipName, float volume)
    {

        AudioClip clip = Resources.Load<AudioClip>(clipName);
        audioSource.clip = clip;
        audioSource.volume = volume;
        audioSource.Play();
    }
	
    public static AudioManager getInstance()
    {
        return instance;
    }

    internal void BallStopped()
    {
      //  if (rolling)
       // {
            rolling = false;
            audioSource.Stop();
       // }
    }

    internal void BallRolling()
    {
      //  if (!rolling)
       // {
            Play("wheel_sound", 1f);
       // }
        rolling = true;
    }

    public void SoundOnOffBtn(int sound_val)
    {
        switch(sound_val)
        {
            case 0:
                AudioListener.volume = 0;
                break;
            case 1:
                AudioListener.volume = 1;
                break;

        }
    }
}
