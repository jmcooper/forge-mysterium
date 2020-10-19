package com.cooperplanet.mysteriummod;

import com.cooperplanet.mysteriummod.proxy.CommonProxy;
import com.cooperplanet.mysteriummod.util.Reference;
import com.cooperplanet.mysteriummod.util.handlers.RegistryHandler;
import com.cooperplanet.mysteriummod.world.ModWorldGenerator;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class Main {
	@Instance
	public static Main instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event) {
		GameRegistry.registerWorldGenerator(new ModWorldGenerator(), 3);
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event) {
		RegistryHandler.initRegistries();
	}
	
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event) {
		
	}
}
