package net.minecraft.client.gui.spectator.categories;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.PlayerMenuObject;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldSettings;

public class TeleportToPlayer implements ISpectatorMenuView, ISpectatorMenuObject
{
    private static final Ordering field_178674_a = Ordering.from(new Comparator()
    {
        public int func_178746_a(NetworkPlayerInfo p_178746_1_, NetworkPlayerInfo p_178746_2_)
        {
            return ComparisonChain.start().compare(p_178746_1_.getGameProfile().getId(), p_178746_2_.getGameProfile().getId()).result();
        }
        @Override
		public int compare(Object p_compare_1_, Object p_compare_2_)
        {
            return this.func_178746_a((NetworkPlayerInfo)p_compare_1_, (NetworkPlayerInfo)p_compare_2_);
        }
    });
    private final List field_178673_b;

    public TeleportToPlayer()
    {
        this(field_178674_a.sortedCopy(Minecraft.getMinecraft().getNetHandler().func_175106_d()));
    }

    public TeleportToPlayer(Collection p_i45493_1_)
    {
        this.field_178673_b = Lists.newArrayList();
        Iterator var2 = field_178674_a.sortedCopy(p_i45493_1_).iterator();

        while (var2.hasNext())
        {
            NetworkPlayerInfo var3 = (NetworkPlayerInfo)var2.next();

            if (var3.getGameType() != WorldSettings.GameType.SPECTATOR)
            {
                this.field_178673_b.add(new PlayerMenuObject(var3.getGameProfile()));
            }
        }
    }

    @Override
	public List func_178669_a()
    {
        return this.field_178673_b;
    }

    @Override
	public IChatComponent func_178670_b()
    {
        return new ChatComponentText("Select a player to teleport to");
    }

    @Override
	public void func_178661_a(SpectatorMenu menu)
    {
        menu.func_178647_a(this);
    }

    @Override
	public IChatComponent getSpectatorName()
    {
        return new ChatComponentText("Teleport to player");
    }

    @Override
	public void func_178663_a(float p_178663_1_, int alpha)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, 16, 16, 256.0F, 256.0F);
    }

    @Override
	public boolean func_178662_A_()
    {
        return !this.field_178673_b.isEmpty();
    }
}
