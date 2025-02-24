/*
 *     Copyright (C) 2018-present Hyperium <https://hyperium.cc/>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published
 *     by the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package cc.hyperium.handlers.handlers.chat;

import net.minecraft.util.IChatComponent;
import java.util.regex.Matcher;

public class WinTrackingChatHandler extends HyperiumChatHandler {
    @Override
    public boolean chatReceived(IChatComponent component, String text) {
        Matcher matcher = regexPatterns.get(ChatRegexType.WIN).matcher(text);
        if (matcher.matches()) {
            String winnersString = matcher.group("winners");
            String[] winners = winnersString.split(", ");

            for (int i = 0; i < winners.length; i++) {
                String winner = winners[i];
                // Means they have a rank prefix. We don't want that
                if (winner.contains(" ")) {
                    winners[i] = winner.split(" ")[1];
                }
            }
        }
        return false;
    }
}
