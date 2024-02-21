public class ConexionBluetooth extends Service {
    // ...

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // ...

        // Iniciar la búsqueda de dispositivos Bluetooth
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);
        mBluetoothAdapter.startDiscovery();

        return START_NOT_STICKY;
    }

    // ...

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Se encontró un dispositivo Bluetooth
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                // ...
            }
        }
    };
}
