package com.bbscncom.tcscaninae.mixins;


import appeng.api.AEApi;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridCache;
import appeng.api.networking.IGridNode;
import appeng.api.networking.storage.IStorageGrid;
import appeng.api.storage.IMEMonitor;
import appeng.api.storage.channels.IItemStorageChannel;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.util.AEPartLocation;
import appeng.me.GridAccessException;
import appeng.me.helpers.AENetworkProxy;
import appeng.tile.AEBaseTile;
import appeng.tile.grid.AENetworkTile;
import appeng.tile.networking.TileCableBus;
import com.bbscncom.tcscaninae.Main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thaumcraft.api.research.ScanningManager;

@Mixin(value = ScanningManager.class, remap = false)
public class MixinScanningManager {

    @Inject(method = "scanTheThing", at = @At(
            value = "HEAD"
    ), cancellable = true)
    private static void scanTheThing(EntityPlayer player, Object object, CallbackInfo ci) {
        try{
            if (object instanceof BlockPos pos) {
                TileEntity tileEntity = player.world.getTileEntity(pos);
                if (tileEntity == null) return;

                if (tileEntity instanceof TileCableBus cable) {
                    IGrid grid = null;
                    try {
                        for (AEPartLocation value : AEPartLocation.values()) {
                            IGridNode gridNode = cable.getGridNode(value);
                            if (gridNode != null) {
                                grid = gridNode.getGrid();
                                break;
                            }
                        }
                    } catch (Exception e) {
                        return;
                    }
                    if (grid == null) return;

                    IMEMonitor<IAEItemStack> storageGrid = ((IStorageGrid) grid.getCache(IStorageGrid.class)).getInventory(AEApi.instance().storage().getStorageChannel(IItemStorageChannel.class));
                    for (IAEItemStack iaeItemStack : storageGrid.getStorageList()) {
                        ItemStack stack = iaeItemStack.getCachedItemStack(1);

                        if (stack != null && !stack.isEmpty()) {
                            ScanningManager.scanTheThing(player, stack);
                        }
                    }
                    ci.cancel();
                    player.sendStatusMessage(new TextComponentString("§a§o" + I18n.translateToLocal("tc.knownobject")), true);
                }
            }
        }catch(Exception e){
            Main.LOGGER.error("scan in ae error",e);
        }
    }
}