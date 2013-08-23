package piefarmer.immunology.common;

import java.util.EnumSet;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class ImmunKeyHandler extends KeyHandler{

	static KeyBinding hanggliderBinding = new KeyBinding("hanggliderBind", Keyboard.KEY_SPACE);
	
	public ImmunKeyHandler() {
        //the first value is an array of KeyBindings, the second is whether or not the call
        //keyDown should repeat as long as the key is down
        super(new KeyBinding[]{hanggliderBinding}, new boolean[]{true});
	}
	@Override
    public String getLabel() {
            return "immunkeybindings";
    }
	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb,
			boolean tickEnd, boolean isRepeat) {
		if(kb == this.hanggliderBinding)
		{
			
		}
		
	}
	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}


}
