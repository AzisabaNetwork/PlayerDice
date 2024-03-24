package azisaba.playerdice;

import com.github.mori01231.lifecore.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;

import static org.bukkit.Bukkit.getServer;

public class UseDice implements Listener {
    private final Map<UUID, Long> RightCooldowns = new HashMap<>();
    private final Map<UUID, Long> LeftCooldowns = new HashMap<>();
    private final Map<UUID, UUID> isSpeaker = new HashMap<>();
    private final Map<UUID, UUID> isMinus = new HashMap<>();
    @EventHandler
    public void InteractDice(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack stack = player.getInventory().getItemInMainHand();
        String id = ItemUtil.getMythicType(stack);
        if(id == null) return;
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            UUID playerID = player.getUniqueId();
            long currentTime = System.currentTimeMillis();
            if (RightCooldowns.containsKey(playerID) && RightCooldowns.get(playerID) > currentTime) {
                return;
            }
            long cooldownTime = currentTime + 5000;
            RightCooldowns.put(playerID, cooldownTime);
            BukkitScheduler scheduler = getServer().getScheduler();
            scheduler.runTaskLater(JavaPlugin.getPlugin(PlayerDice.class), () -> RightCooldowns.remove(playerID), 100);

            if(id.equals("take_saikoro_dice")) {PlayerDice6D(player);}
            if(id.equals("take_tyouhan_dice")) {PlayerDice2D6(player);}
            if(id.equals("take_tinntiro_dice")) {PlayerDice3D6(player);}
            if(id.equals("プレイヤー用ダイス_10d")) {PlayerDice10D(player);}
            if(id.equals("プレイヤー用ダイス_13d")) {PlayerDice13D(player);}
            if(id.equals("プレイヤー用ダイス_50d")) {PlayerDice50D(player);}
            if(id.equals("プレイヤー用ダイス_100d")) {PlayerDice100D(player);}
        }
    }
    @EventHandler
    public void SwingDice(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack stack = player.getInventory().getItemInMainHand();
        String id = ItemUtil.getMythicType(stack);
        if (id == null) return;
        if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            UUID playerID = player.getUniqueId();
            long currentTime = System.currentTimeMillis();
            if (LeftCooldowns.containsKey(playerID) && LeftCooldowns.get(playerID) > currentTime) return;
            long cooldownTime = currentTime + 3000;
            LeftCooldowns.put(playerID, cooldownTime);
            BukkitScheduler scheduler = getServer().getScheduler();
            scheduler.runTaskLater(JavaPlugin.getPlugin(PlayerDice.class), () -> LeftCooldowns.remove(playerID), 60);

            if (id.equals("Iga_battle_dice_100")) {
                if (player.isSneaking()) {
                    if (!isSpeaker.containsKey(playerID)) {
                        isSpeaker.put(playerID, playerID);
                        player.sendMessage("スピーカーモードに変更しました");
                    } else {
                        isSpeaker.remove(playerID);
                        player.sendMessage("スピーカーモードをオフにしました");
                    }
                } else {
                    if (!isMinus.containsKey(playerID)) {
                        isMinus.put(playerID, playerID);
                        player.sendMessage("マイナスモードに変更しました");
                    } else {
                        isMinus.remove(playerID);
                        player.sendMessage("プラスモードに変更しました");
                    }
                }
            }
        }
    }
    public void PlayerDice6D(Player player) {
        Random random = new Random();
        int dice = random.nextInt(6) + 1;
        player.sendMessage("§f§l" + player.getName() + "§c§lさんの6ダイスの出目は§e§l" + dice + "§c§lです！");

        int radius = 35;
        Location center = player.getLocation();
        World world = player.getWorld();
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (target != player && target.getWorld() == world && target.getLocation().distance(center) <= radius) {
                target.sendMessage("§f§l" + player.getName() + "§c§lさんの6ダイスの出目は§e§l" + dice + "§c§lです！");
            }
        }
    }
    public void PlayerDice2D6(Player player) {
        Random random = new Random();
        int dice1 = random.nextInt(6) + 1;
        int dice2 = random.nextInt(6) + 1;
        int dice3 = dice1 + dice2;
        player.sendMessage("§f§l" + player.getName() + "§c§lさんの2連6ダイスの出目は§e§l" + dice1 + "§f§l,§e§l" + dice2 +  "§c§lで、合計は§e§l" + dice3 + "§c§lです！");

        int radius = 35;
        Location center = player.getLocation();
        World world = player.getWorld();
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (target != player && target.getWorld() == world && target.getLocation().distance(center) <= radius) {
                target.sendMessage("§f§l" + player.getName() + "§c§lさんの2連6ダイスの出目は§e§l" + dice1 + "§f§l,§e§l" + dice2 +  "§c§lで、合計は§e§l" + dice3 + "§c§lです！");
            }
        }
    }
    public void PlayerDice3D6(Player player) {
        Random random = new Random();
        int dice1 = random.nextInt(6) + 1;
        int dice2 = random.nextInt(6) + 1;
        int dice3 = random.nextInt(6) + 1;
        player.sendMessage("§f§l" + player.getName() + "§c§lさんの3連6ダイスの出目は§e§l" + dice1 + "§f§l,§e§l" + dice2 + "§f§l,§e§l" + dice3 + "§c§lです！");

        int radius = 35;
        Location center = player.getLocation();
        World world = player.getWorld();
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (target != player && target.getWorld() == world && target.getLocation().distance(center) <= radius) {
                target.sendMessage("§f§l" + player.getName() + "§c§lさんの3連6ダイスの出目は§e§l" + dice1 + "§f§l,§e§l" + dice2 + "§f§l,§e§l" + dice3 + "§c§lです！");
            }
        }
    }
    public void PlayerDice10D(Player player) {
        Random random = new Random();
        int dice = random.nextInt(10) + 1;
        player.sendMessage("§f§l" + player.getName() + "§c§lさんの10ダイスの出目は§e§l" + dice + "§c§lです！");

        int radius = 35;
        Location center = player.getLocation();
        World world = player.getWorld();
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (target != player && target.getWorld() == world && target.getLocation().distance(center) <= radius) {
                target.sendMessage("§f§l" + player.getName() + "§c§lさんの10ダイスの出目は§e§l" + dice + "§c§lです！");
            }
        }
    }
    public void PlayerDice13D(Player player) {
        Random random = new Random();
        int dice = random.nextInt(13) + 1;
        player.sendMessage("§f§l" + player.getName() + "§c§lさんの13ダイスの出目は§e§l" + dice + "§c§lです！");

        int radius = 35;
        Location center = player.getLocation();
        World world = player.getWorld();
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (target != player && target.getWorld() == world && target.getLocation().distance(center) <= radius) {
                target.sendMessage("§f§l" + player.getName() + "§c§lさんの13ダイスの出目は§e§l" + dice + "§c§lです！");
            }
        }
    }
    public void PlayerDice50D(Player player) {
        Random random = new Random();
        int dice = random.nextInt(50) + 1;
        player.sendMessage("§f§l" + player.getName() + "§c§lさんの50ダイスの出目は§e§l" + dice + "§c§lです！");

        int radius = 35;
        Location center = player.getLocation();
        World world = player.getWorld();
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (target != player && target.getWorld() == world && target.getLocation().distance(center) <= radius) {
                target.sendMessage("§f§l" + player.getName() + "§c§lさんの50ダイスの出目は§e§l" + dice + "§c§lです！");
            }
        }
    }
    public void PlayerDice100D(Player player) {
        Random random = new Random();
        int dice = random.nextInt(100) + 1;
        player.sendMessage("§f§l" + player.getName() + "§c§lさんの100ダイスの出目は§e§l" + dice + "§c§lです！");

        int radius = 35;
        Location center = player.getLocation();
        World world = player.getWorld();
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (target != player && target.getWorld() == world && target.getLocation().distance(center) <= radius) {
                target.sendMessage("§f§l" + player.getName() + "§c§lさんの100ダイスの出目は§e§l" + dice + "§c§lです！");
            }
        }
    }

    @EventHandler
    public void BattleDice(PlayerInteractAtEntityEvent e) {
        Player player = e.getPlayer();
        ItemStack stack = player.getInventory().getItemInMainHand();
        String id = ItemUtil.getMythicType(stack);
        if (id == null || !id.equals("Iga_battle_dice_100")) return;
        UUID playerID = player.getUniqueId();
        long currentTime = System.currentTimeMillis();
        if (RightCooldowns.containsKey(playerID) && RightCooldowns.get(playerID) > currentTime) {
            return;
        }
        long cooldownTime = currentTime + 5000;
        RightCooldowns.put(playerID, cooldownTime);
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.runTaskLater(JavaPlugin.getPlugin(PlayerDice.class), () -> RightCooldowns.remove(playerID), 100);

        Random random = new Random();
        Player player1 = e.getPlayer();
        Entity player2 = e.getRightClicked();
        int dice1 = random.nextInt(100) + 1;
        int dice2 = random.nextInt(100) + 1;

        if (!isMinus.containsKey(playerID)) {
            player1.sendMessage("§f§l" + player1.getName() + "§c§lさんと§f§l" + player2.getName() + "§c§lさんの§e§lプラス100式§c§lを始めます！");
            player2.sendMessage("§f§l" + player1.getName() + "§c§lさんと§f§l" + player2.getName() + "§c§lさんの§e§lプラス100式§c§lを始めます！");
        } else {
            player1.sendMessage("§f§l" + player1.getName() + "§c§lさんと§f§l" + player2.getName() + "§c§lさんの§e§lマイナス100式§c§lを始めます！");
            player2.sendMessage("§f§l" + player1.getName() + "§c§lさんと§f§l" + player2.getName() + "§c§lさんの§e§lマイナス100式§c§lを始めます！");
        }

        scheduler.runTaskLater(JavaPlugin.getPlugin(PlayerDice.class), () -> player1.sendMessage("§f§l" + player1.getName() + "§c§lさんの100ダイスの出目は§e§l" + dice1 + "§c§lです！"), 20);
        scheduler.runTaskLater(JavaPlugin.getPlugin(PlayerDice.class), () -> player2.sendMessage("§f§l" + player1.getName() + "§c§lさんの100ダイスの出目は§e§l" + dice1 + "§c§lです！"), 20);
        scheduler.runTaskLater(JavaPlugin.getPlugin(PlayerDice.class), () -> player1.sendMessage("§f§l" + player2.getName() + "§c§lさんの100ダイスの出目は§e§l" + dice2 + "§c§lです！"), 40);
        scheduler.runTaskLater(JavaPlugin.getPlugin(PlayerDice.class), () -> player2.sendMessage("§f§l" + player2.getName() + "§c§lさんの100ダイスの出目は§e§l" + dice2 + "§c§lです！"), 40);

        if (dice1 == dice2) {
            scheduler.runTaskLater(JavaPlugin.getPlugin(PlayerDice.class), () -> player1.sendMessage("§e§l引き分け§c§lです！"), 60);
            scheduler.runTaskLater(JavaPlugin.getPlugin(PlayerDice.class), () -> player2.sendMessage("§e§l引き分け§c§lです！"), 60);
            return;
        }
        if (dice1 > dice2) {
            if (!isMinus.containsKey(playerID)) {
                scheduler.runTaskLater(JavaPlugin.getPlugin(PlayerDice.class), () -> player1.sendMessage("§f§l" + player1.getName() + "§c§lさんが§e§l勝ちました！"), 60);
                scheduler.runTaskLater(JavaPlugin.getPlugin(PlayerDice.class), () -> player2.sendMessage("§f§l" + player1.getName() + "§c§lさんが§e§l勝ちました！"), 60);
            } else {
                scheduler.runTaskLater(JavaPlugin.getPlugin(PlayerDice.class), () -> player1.sendMessage("§f§l" + player2.getName() + "§c§lさんが§e§l勝ちました！"), 60);
                scheduler.runTaskLater(JavaPlugin.getPlugin(PlayerDice.class), () -> player2.sendMessage("§f§l" + player2.getName() + "§c§lさんが§e§l勝ちました！"), 60);
            }
        } else {
            if (!isMinus.containsKey(playerID)) {
                scheduler.runTaskLater(JavaPlugin.getPlugin(PlayerDice.class), () -> player1.sendMessage("§f§l" + player2.getName() + "§c§lさんが§e§l勝ちました！"), 60);
                scheduler.runTaskLater(JavaPlugin.getPlugin(PlayerDice.class), () -> player2.sendMessage("§f§l" + player2.getName() + "§c§lさんが§e§l勝ちました！"), 60);
            } else {
                scheduler.runTaskLater(JavaPlugin.getPlugin(PlayerDice.class), () -> player1.sendMessage("§f§l" + player1.getName() + "§c§lさんが§e§l勝ちました！"), 60);
                scheduler.runTaskLater(JavaPlugin.getPlugin(PlayerDice.class), () -> player2.sendMessage("§f§l" + player1.getName() + "§c§lさんが§e§l勝ちました！"), 60);
            }
        }
    }
}
