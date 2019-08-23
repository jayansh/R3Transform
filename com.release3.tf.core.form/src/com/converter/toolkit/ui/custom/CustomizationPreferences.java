package com.converter.toolkit.ui.custom;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class CustomizationPreferences extends VIAStorage {

    Vector<Trigger> trigger = new Vector<Trigger>();
    Vector<RecordGroup> recordGroups = new Vector<RecordGroup>();
    Vector<BlockCust> blockCust = new Vector<BlockCust>();


    public void addTrigger(Trigger trg) {
        Iterator<Trigger> itr = trigger.iterator();
        while (itr.hasNext()) {
            Trigger t = itr.next();

            if (t.getItem() == null) {
                if ((t.getBlock().equals(trg.getBlock())) && 
                    (t.getJsfattr().equals(trg.getJsfattr()))) {
                    trigger.remove(t);
                    break;
                }

            } else {

                if ((t.getBlock().equals(trg.getBlock())) && 
                    (t.getItem().equals(trg.getItem())) && 
                    (t.getJsfattr().equals(trg.getJsfattr()))) {
                    trigger.remove(t);
                    break;
                }
            }
        }
        trigger.add(trg);
    }

    public List<Trigger> getTrigger() {
        return trigger;
    }

    public void addRecordGroup(RecordGroup rg) {
        Iterator<RecordGroup> itr = recordGroups.iterator();
        while (itr.hasNext()) {
            RecordGroup t = itr.next();
            if ((t.getName().equals(rg.getName()))) {
                recordGroups.remove(t);
                break;
            }
        }
        recordGroups.add(rg);
    }

    public List getRecordGroup() {
        return recordGroups;
    }

    public void addBlockCust(BlockCust rg) {
        Iterator<BlockCust> itr = blockCust.iterator();
        while (itr.hasNext()) {
            BlockCust t = itr.next();
            if ((t.getName().equals(rg.getName()))) {
                blockCust.remove(t);
                break;
            }
        }
        blockCust.add(rg);
    }

    public List getBlockCust() {
        return blockCust;
    }


}
