package kidtoys.az.kidphone;

/**
 * Phone calls for kids
 */
public class CallMode extends BaseMode{

    public CallMode(Phone phone) throws Exception{
        super(phone);
        onRefresh();
    }

    @Override
    public void onClick(FunnyButton funnyButton) {
        if (funnyButton.getKeyMode() == FunnyButton.KeyMode.Numbers) {
            String number = funnyButton.getNumbersText();
            if (number.length() > 0) {
                phone.getAudio().playKeypadTones(number.charAt(0));
            }
        }
    }

    @Override
    public   void onRefresh(){
        phone.getAudio().playCallAnyOne();
        phone.changeKeys(FunnyButton.KeyMode.Numbers);
        phone.getDisplay().clear();
    }

    @Override
    public   void onSave(){
        //putState("key",object);
    }
}
