package net.minecraft.client.resources;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Charsets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractResourcePack implements IResourcePack
{
    private static final Logger resourceLog = LogManager.getLogger();
    public final File resourcePackFile;

    public AbstractResourcePack(File resourcePackFileIn)
    {
        this.resourcePackFile = resourcePackFileIn;
    }

    private static String locationToName(ResourceLocation location)
    {
        return String.format("%s/%s/%s", new Object[] {"assets", location.getResourceDomain(), location.getResourcePath()});
    }

    protected static String getRelativeName(File p_110595_0_, File p_110595_1_)
    {
        return p_110595_0_.toURI().relativize(p_110595_1_.toURI()).getPath();
    }

    @Override
	public InputStream getInputStream(ResourceLocation location) throws IOException
    {
        return this.getInputStreamByName(locationToName(location));
    }

    @Override
	public boolean resourceExists(ResourceLocation location)
    {
        return this.hasResourceName(locationToName(location));
    }

    protected abstract InputStream getInputStreamByName(String var1) throws IOException;

    protected abstract boolean hasResourceName(String var1);

    protected void logNameNotLowercase(String p_110594_1_)
    {
        resourceLog.warn("ResourcePack: ignored non-lowercase namespace: %s in %s", new Object[] {p_110594_1_, this.resourcePackFile});
    }

    @Override
	public IMetadataSection getPackMetadata(IMetadataSerializer p_135058_1_, String p_135058_2_) throws IOException
    {
        return readMetadata(p_135058_1_, this.getInputStreamByName("pack.mcmeta"), p_135058_2_);
    }

    static IMetadataSection readMetadata(IMetadataSerializer p_110596_0_, InputStream p_110596_1_, String p_110596_2_)
    {
        JsonObject var3 = null;
        BufferedReader var4 = null;

        try
        {
            var4 = new BufferedReader(new InputStreamReader(p_110596_1_, Charsets.UTF_8));
            var3 = (new JsonParser()).parse(var4).getAsJsonObject();
        }
        catch (RuntimeException var9)
        {
            throw new JsonParseException(var9);
        }
        finally
        {
            IOUtils.closeQuietly(var4);
        }

        return p_110596_0_.parseMetadataSection(p_110596_2_, var3);
    }

    @Override
	public BufferedImage getPackImage() throws IOException
    {
        return TextureUtil.readBufferedImage(this.getInputStreamByName("pack.png"));
    }

    @Override
	public String getPackName()
    {
        return this.resourcePackFile.getName();
    }
}
