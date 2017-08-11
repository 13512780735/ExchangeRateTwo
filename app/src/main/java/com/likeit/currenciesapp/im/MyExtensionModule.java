package com.likeit.currenciesapp.im;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import io.rong.common.RLog;
import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.manager.InternalModuleManager;
import io.rong.imkit.plugin.CombineLocationPlugin;
import io.rong.imkit.plugin.DefaultLocationPlugin;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.plugin.ImagePlugin;
import io.rong.imkit.widget.provider.FilePlugin;
import io.rong.imlib.model.Conversation;



public class MyExtensionModule extends DefaultExtensionModule {
    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
//        List<IPluginModule> pluginModules =  getPluginModules(conversationType);
        List<IPluginModule> pluginModules =  super.getPluginModules(conversationType);
        Logger.d("pluginModules  :"+pluginModules.size());

//        if(pluginModules.size()>2) {
//            for (int i = pluginModules.size()-1; i >1; i--) {
//                pluginModules.remove(i);
//            }
//        }

            return pluginModules;
    }


//
//    public List<IPluginModule> getRongPluginModules(Conversation.ConversationType conversationType) {
//        ArrayList pluginModuleList = new ArrayList();
//        ImagePlugin image = new ImagePlugin();
//        FilePlugin file = new FilePlugin();
//        pluginModuleList.add(image);
//
//        if(conversationType.equals(Conversation.ConversationType.GROUP) || conversationType.equals(Conversation.ConversationType.DISCUSSION) || conversationType.equals(Conversation.ConversationType.PRIVATE)) {
//            pluginModuleList.addAll(InternalModuleManager.getInstance().getExternalPlugins(conversationType));
//        }
//
//        pluginModuleList.add(file);
//        return pluginModuleList;
//    }
}
