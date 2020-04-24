package com.dbydd.micro_machinery.interfaces;

import com.dbydd.micro_machinery.EnumType.EnumMMFETileEntityStatus;
import com.dbydd.micro_machinery.blocks.tileentities.MMFEMachineBase;
import com.dbydd.micro_machinery.blocks.tileentities.TileEntityEnergyCableWithoutGenerateForce;
import com.dbydd.micro_machinery.energynetwork.EnergyNetWorkSpecialPackge;
import com.dbydd.micro_machinery.energynetwork.SurrondingsState;
import com.dbydd.micro_machinery.util.EnergyNetWorkUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public interface IMMFETransfer {

    static EnumMMFETileEntityStatus getStatusInThisPos(BlockPos pos, World world) {
        int blockX = pos.getX();
        int blockY = pos.getY();
        int blockZ = pos.getZ();
        int outputCount = 0;
        int inputCount = 0;
        for (int x = -1; x <= 1; x++) {
            BlockPos position = new BlockPos(blockX + x, blockY, blockZ);
            if (world.getTileEntity(position) instanceof MMFEMachineBase) {
                if (((MMFEMachineBase) world.getTileEntity(position)).getStatus() == EnumMMFETileEntityStatus.OUTPUT) {
                    outputCount++;
                }
                if (((MMFEMachineBase) world.getTileEntity(position)).getStatus() == EnumMMFETileEntityStatus.INPUT) {
                    inputCount++;
                }
            }
        }
        for (int y = -1; y <= 1; y++) {
            BlockPos position = new BlockPos(blockX, blockY + y, blockZ);
            if (world.getTileEntity(position) instanceof MMFEMachineBase) {
                if (((MMFEMachineBase) world.getTileEntity(position)).getStatus() == EnumMMFETileEntityStatus.OUTPUT) {
                    outputCount++;
                }
                if (((MMFEMachineBase) world.getTileEntity(position)).getStatus() == EnumMMFETileEntityStatus.INPUT) {
                    inputCount++;
                }
            }
        }
        for (int z = -1; z <= 1; z++) {
            BlockPos position = new BlockPos(blockX, blockY, blockZ + z);
            if (world.getTileEntity(position) instanceof MMFEMachineBase) {
                if (((MMFEMachineBase) world.getTileEntity(position)).getStatus() == EnumMMFETileEntityStatus.OUTPUT) {
                    outputCount++;
                }
                if (((MMFEMachineBase) world.getTileEntity(position)).getStatus() == EnumMMFETileEntityStatus.INPUT) {
                    inputCount++;
                }
            }
        }
        if (outputCount > 0 && inputCount > 0) return EnumMMFETileEntityStatus.CABLE;
        else if (outputCount > 0) return EnumMMFETileEntityStatus.INPUT;
        else if (inputCount > 0) return EnumMMFETileEntityStatus.OUTPUT;
        return EnumMMFETileEntityStatus.CABLE;
    }

    static SurrondingsState getNearbyCables(BlockPos pos, World world) {
        SurrondingsState state = new SurrondingsState();
        for (EnumFacing facing : EnergyNetWorkUtils.getFacings()) {
            TileEntity te = world.getTileEntity(pos.offset(facing));
            if (te instanceof TileEntityEnergyCableWithoutGenerateForce) {
                state.setStatusInFacing(facing, EnumMMFETileEntityStatus.CABLE);
            }
        }
        return state;
    }

    default <T> T traverseNearbyCable(IMethodInterface<T> method) {
        return (T) method.Method();
    }

    default SurrondingsState getNearbyCablesWithoutFacing(BlockPos pos, World world) {
        return new SurrondingsState(pos, world);
    }

    default IEnergyStorage getOffsetBlockEnergyCapacity(EnumFacing facing, BlockPos pos, World world) {
        return world.getTileEntity(pos.offset(facing)).getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());
    }

    default boolean isOffsetBlockHasEnergyCapacity(EnumFacing facing, BlockPos pos, World world) {
        TileEntity te = world.getTileEntity(pos.offset(facing));
        if (te != null && !(te instanceof TileEntityEnergyCableWithoutGenerateForce)) {
            return te.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite());
        }
        return false;
    }

    default boolean isOffsetBlockCable(EnumFacing facing, BlockPos pos, World world) {
        TileEntity te = world.getTileEntity(pos.offset(facing));
        if (te != null && te instanceof TileEntityEnergyCableWithoutGenerateForce) {
            return true;
        }
        return false;
    }

    EnergyNetWorkSpecialPackge askForPackage(EnumFacing facing);

    EnergyNetWorkSpecialPackge replyPackage(EnumFacing facing);

    public void notifyNearbyCables();

    public void notifyByNearbyCables(EnergyNetWorkSpecialPackge pack);

    public void notifyByLastCables();

}
