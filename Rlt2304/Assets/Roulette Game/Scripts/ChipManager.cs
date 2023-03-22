using UnityEngine;
using System.Collections;
using System;

public static class ChipManager {

    internal static Chip selected = GameObject.Find("chip1").GetComponent<Chip>();

    internal static int GetSelectedValue()
    {
        return selected.value;
    }
}