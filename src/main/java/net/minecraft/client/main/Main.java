package net.minecraft.client.main;

import java.io.File;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.List;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.NonOptionArgumentSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class Main
{
    private static final String __OBFID = "CL_00001461";

    public static void main(String[] args)
    {
        System.setProperty("java.net.preferIPv4Stack", "true");
        OptionParser optionParser = new OptionParser();
        optionParser.allowsUnrecognizedOptions();
        optionParser.accepts("demo");
        optionParser.accepts("fullscreen");
        ArgumentAcceptingOptionSpec serverArg = optionParser.accepts("server").withRequiredArg();
        ArgumentAcceptingOptionSpec portArg = optionParser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(25565);
        ArgumentAcceptingOptionSpec gameDirArg = optionParser.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."));
        ArgumentAcceptingOptionSpec assetsDirArg = optionParser.accepts("assetsDir").withRequiredArg().ofType(File.class);
        ArgumentAcceptingOptionSpec resourcePackDirArg = optionParser.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
        ArgumentAcceptingOptionSpec proxyHostArg = optionParser.accepts("proxyHost").withRequiredArg();
        ArgumentAcceptingOptionSpec proxyPortArg = optionParser.accepts("proxyPort").withRequiredArg().defaultsTo("8080", new String[0]).ofType(Integer.class);
        ArgumentAcceptingOptionSpec proxyUserArg = optionParser.accepts("proxyUser").withRequiredArg();
        ArgumentAcceptingOptionSpec proxyPassArg = optionParser.accepts("proxyPass").withRequiredArg();
        ArgumentAcceptingOptionSpec usernameArg = optionParser.accepts("username").withRequiredArg().defaultsTo("Player" + Minecraft.getSystemTime() % 1000L);
        ArgumentAcceptingOptionSpec uuidArg = optionParser.accepts("uuid").withRequiredArg();
        ArgumentAcceptingOptionSpec accessTokenArg = optionParser.accepts("accessToken").withRequiredArg().required();
        ArgumentAcceptingOptionSpec versionArg = optionParser.accepts("version").withRequiredArg().required();
        ArgumentAcceptingOptionSpec widthArg = optionParser.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo(854);
        ArgumentAcceptingOptionSpec heightArg = optionParser.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo(480);
        NonOptionArgumentSpec nonOptions = optionParser.nonOptions();
        OptionSet optionSet = optionParser.parse(args);
        List list = optionSet.valuesOf(nonOptions);
        String socketAddress = (String)optionSet.valueOf(proxyHostArg);
        Proxy noProxy = Proxy.NO_PROXY;

        if (socketAddress != null)
        {
            try
            {
                noProxy = new Proxy(Type.SOCKS, new InetSocketAddress(socketAddress, ((Integer)optionSet.valueOf(proxyPortArg)).intValue()));
            }
            catch (Exception ignored)
            {
                ;
            }
        }

        final String var22 = (String)optionSet.valueOf(proxyUserArg);
        final String var23 = (String)optionSet.valueOf(proxyPassArg);

        if (!noProxy.equals(Proxy.NO_PROXY) && func_110121_a(var22) && func_110121_a(var23))
        {
            Authenticator.setDefault(new Authenticator()
            {
                private static final String __OBFID = "CL_00000828";
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication(var22, var23.toCharArray());
                }
            });
        }

        int var24 = (Integer) optionSet.valueOf(widthArg);
        int var25 = (Integer) optionSet.valueOf(heightArg);
        boolean isFullscreen = optionSet.has("fullscreen");
        boolean isDemo = optionSet.has("demo");
        String var28 = (String)optionSet.valueOf(versionArg);
        File var29 = (File)optionSet.valueOf(gameDirArg);
        File assetsFile = optionSet.has(assetsDirArg) ? (File)optionSet.valueOf(assetsDirArg) : new File(var29, "assets/");
        File resourcepacksFile = optionSet.has(resourcePackDirArg) ? (File)optionSet.valueOf(resourcePackDirArg) : new File(var29, "resourcepacks/");
        String var32 = optionSet.has(uuidArg) ? (String)uuidArg.value(optionSet) : (String)usernameArg.value(optionSet);
        Session session = new Session((String)usernameArg.value(optionSet), var32, (String)accessTokenArg.value(optionSet));
        Minecraft var34 = new Minecraft(session, var24, var25, isFullscreen, isDemo, var29, assetsFile, resourcepacksFile, noProxy, var28);
        String var35 = (String)optionSet.valueOf(serverArg);

        if (var35 != null)
        {
            var34.setServer(var35, (Integer) optionSet.valueOf(portArg));
        }

        Runtime.getRuntime().addShutdownHook(new Thread("Client Shutdown Thread")
        {
            public void run()
            {
                Minecraft.stopIntegratedServer();
            }
        });

        if (!list.isEmpty())
        {
            System.out.println("Completely ignored arguments: " + list);
        }

        Thread.currentThread().setName("Client thread");
        var34.run();
    }

    private static boolean func_110121_a(String par0Str)
    {
        return par0Str != null && !par0Str.isEmpty();
    }
}
