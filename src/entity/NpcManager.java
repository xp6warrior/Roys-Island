package entity;

import handler.MapHandler;
import main.GamePanel;
import main.Util;

import java.awt.*;
import java.util.Arrays;

public class NpcManager {
    public final Entity[] NPCs = new Entity[1];

    private final GamePanel gp;
    public NpcManager(GamePanel gp) {
        this.gp = gp;
    }

    public void reloadNPCs(MapHandler mapH) {
        Arrays.fill(NPCs, null);

        if (mapH == MapHandler.mapIsland) {
            Entity roy = new NPC_Roy(gp);
            roy.worldX = 31 * Util.tileSize;
            roy.worldY = 8 * Util.tileSize;
            NPCs[0] = roy;

        }
    }

    public void draw(Graphics2D g2d) {
        for (Entity npc : NPCs) {

            if (npc != null) {
                npc.screenX = npc.worldX - gp.player.worldX + gp.player.screenX;
                npc.screenY = npc.worldY - gp.player.worldY + gp.player.screenY;

                if (npc.screenX > -Util.tileSize && npc.screenX < Util.windowX
                        && npc.screenY > -Util.tileSize && npc.screenY < Util.windowY) {

                    npc.draw(g2d);
                }
            }

        }
    }

}
