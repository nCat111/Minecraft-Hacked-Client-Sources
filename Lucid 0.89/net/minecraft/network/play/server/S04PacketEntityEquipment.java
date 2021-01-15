package net.minecraft.network.play.server;

import java.io.IOException;

import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S04PacketEntityEquipment implements Packet
{
    private int field_149394_a;
    private int field_149392_b;
    private ItemStack field_149393_c;

    public S04PacketEntityEquipment() {}

    public S04PacketEntityEquipment(int p_i45221_1_, int p_i45221_2_, ItemStack p_i45221_3_)
    {
        this.field_149394_a = p_i45221_1_;
        this.field_149392_b = p_i45221_2_;
        this.field_149393_c = p_i45221_3_ == null ? null : p_i45221_3_.copy();
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    @Override
	public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.field_149394_a = buf.readVarIntFromBuffer();
        this.field_149392_b = buf.readShort();
        this.field_149393_c = buf.readItemStackFromBuffer();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    @Override
	public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeVarIntToBuffer(this.field_149394_a);
        buf.writeShort(this.field_149392_b);
        buf.writeItemStackToBuffer(this.field_149393_c);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleEntityEquipment(this);
    }

    public ItemStack func_149390_c()
    {
        return this.field_149393_c;
    }

    public int func_149389_d()
    {
        return this.field_149394_a;
    }

    public int func_149388_e()
    {
        return this.field_149392_b;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    @Override
	public void processPacket(INetHandler handler)
    {
        this.processPacket((INetHandlerPlayClient)handler);
    }
}
