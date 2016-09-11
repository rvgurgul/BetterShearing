package legorvegenine;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterShearing extends JavaPlugin implements Listener
{
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable()
	{
		
	}
	
	@EventHandler
	public void onSheepShear(PlayerShearEntityEvent e)
	{
		if(e.getEntity() instanceof Sheep)
		{
			e.setCancelled(true);
			
			ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
			if(item.getType() == Material.SHEARS)
			{
				Sheep sheep = (Sheep)e.getEntity();
				Player p = e.getPlayer();
				
				Random RNG = new Random();
				int woolCount = RNG.nextInt(3) + 1;
				int lootReward = RNG.nextInt(item.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS) + 1);
				
				short color = 0;
				switch(sheep.getColor())
				{
				case ORANGE: color = 1; break;
				case MAGENTA: color = 2; break;
				case LIGHT_BLUE: color = 3; break;
				case YELLOW: color = 4; break;
				case LIME: color = 5; break;
				case PINK: color = 6; break;
				case GRAY: color = 7; break;
				case SILVER: color = 8; break;
				case CYAN: color = 9; break;
				case PURPLE: color = 10; break;
				case BLUE: color = 11; break;
				case BROWN: color = 12; break;
				case GREEN: color = 13; break;
				case RED: color = 14; break;
				case BLACK: color = 15; break;
				default: color = 0; break;
				}
				
				ItemStack wool = new ItemStack(Material.WOOL, woolCount + lootReward, color);
				
				sheep.getWorld().dropItemNaturally(sheep.getLocation(), wool);
				sheep.setSheared(true);
				
				p.playSound(p.getLocation(), Sound.ENTITY_SHEEP_SHEAR, 1, 1);
			}
		}
	}
	
	@EventHandler
	public void prepareAnvil(PrepareAnvilEvent e)
	{
		AnvilInventory inv = e.getInventory();
		
		try
		{
			ItemStack item1 = inv.getContents()[0];
			getLogger().info(item1.toString());
			ItemStack item2 = inv.getContents()[1];
			getLogger().info(item2.toString());
			
			if(item1.getType() == Material.SHEARS && item2.getType() == Material.ENCHANTED_BOOK)	
			{
				//int enchLevel = item2.getItemMeta().getEnchantLevel(Enchantment.LOOT_BONUS_MOBS);
				//if(enchLevel < 1 || 3 < enchLevel) {getLogger().info("EL IS TOO LOW: " + enchLevel);return;}
				
				ItemStack result = item1.clone();
				
				ItemMeta meta = result.getItemMeta();
				meta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 3, true);
				result.setItemMeta(meta);
				
				e.setResult(result);
			}
		}
		catch(NullPointerException ex)
		{
			return;
		}
	}
}
